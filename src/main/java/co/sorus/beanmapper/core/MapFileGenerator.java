package co.sorus.beanmapper.core;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class MapFileGenerator {

    private ProcessingEnvironment env;
    private Filer filer;

    private Configuration cfg;

    public MapFileGenerator(ProcessingEnvironment processingEnv) {
        this.env = processingEnv;
        init();
    }

    private void init() {

        // Initialize FreeMarker
        cfg = new Configuration(Configuration.VERSION_2_3_25);
        cfg.setClassForTemplateLoading(getClass(), "");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLogTemplateExceptions(false);

        // Initiatize annotation processor
        filer = env.getFiler();
    }

    public void generate(BeanMapping mapping) {
        Map<String, Object> root = new HashMap<>();

        root.put("qualifiedName", mapping.to.getQualifiedName().toString());
        root.put("toBean", mapping.to.getSimpleName().toString());
        root.put("packageName", getPackage(mapping.to.getQualifiedName().toString()));
        root.put("fromBean", mapping.from.getQualifiedName().toString());

        List<Map<String, String>> props = new ArrayList<>(mapping.props.size());
        root.put("properties", props);
        int index = 0;
        for (BeanMapping.Property property : mapping.props) {
            Map<String, String> prop = new HashMap<>();
            prop.put("index", Integer.toString(index));
            index++;

            prop.put("fromType", type(property.from));
            prop.put("complex", (property.isComplex ? "true" : "false"));
            if (property.isComplex) {
                handleComplex(property, prop);
            } else {
                prop.put("fromGetter", property.from.getSimpleName().toString());
                prop.put("toSetter", accessor(property.to.toString(), "setter"));

                prop.put("mapperUsed", "false");
                if (property.mapperClass != null) {
                    prop.put("mapperUsed", "true");
                    prop.put("mapperClass", property.mapperClass.getQualifiedName().toString());
                    prop.put("mapperMethod", property.mapperMethod.getSimpleName().toString());
                }
            }

            prop.put("mapperAdditional", Boolean.toString(property.implicitConversion));

            props.add(prop);
        }

        createFile(root);
    }

    private void handleComplex(BeanMapping.Property property, Map<String, String> prop) {
        String functions = property.methods.stream().map(m -> getName(m)).collect(Collectors.joining("."));
        prop.put("fromGetter", functions);
        Element lastElement = property.methods.get(property.methods.size() - 1);
        prop.put("fromType", type(lastElement));

        prop.put("toSetter", accessor(property.to.toString(), "setter"));
        prop.put("mapperUsed", "false");
        if (property.mapperClass != null) {
            prop.put("mapperUsed", "true");
            prop.put("mapperClass", property.mapperClass.getQualifiedName().toString());
            prop.put("mapperMethod", property.mapperMethod.getSimpleName().toString());
        }
    }

    private String getName(Element el) {
        if (el instanceof ExecutableElement)
            return el.getSimpleName().toString() + "()";
        return el.getSimpleName().toString();
    }

    private void createFile(Map<String, Object> root) {
        try {
            Template template = cfg.getTemplate("beanmapper.ftl");
            String filename = root.get("qualifiedName") + "Generator";
            JavaFileObject fileObject = filer.createSourceFile(filename);
            try (Writer out = fileObject.openWriter()) {
                template.process(root, out);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private String getPackage(String qualifiedName) {
        int idx = qualifiedName.lastIndexOf('.');
        if (idx == -1)
            return "";
        else
            return qualifiedName.substring(0, idx);
    }

    private String accessor(String property, String accessType) {
        property = property.substring(0, 1).toUpperCase() + property.substring(1);
        if (accessType.equals("getter"))
            return "get" + property;
        else if (accessType.equals("setter"))
            return "set" + property;
        else
            return null;
    }

    private String type(Element element) {
        if (element instanceof VariableElement)
            return element.asType().toString();
        else if (element instanceof ExecutableElement) {
            TypeMirror returnType = ((ExecutableElement) element).getReturnType();
            return returnType.toString();
        }
        return "";
    }
}
