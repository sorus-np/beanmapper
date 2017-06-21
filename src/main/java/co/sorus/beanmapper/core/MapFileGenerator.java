package co.sorus.beanmapper.core;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
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

        createFile(root);
    }

    private void createFile(Map<String, Object> root) {
        try {
            Template template = cfg.getTemplate("beanmapper.ftl");
            String filename = root.get("qualifiedName") + "Generator";
            JavaFileObject fileObject = filer.createSourceFile(filename);
            Writer out = fileObject.openWriter();
            template.process(root, out);
            out.close();
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
}
