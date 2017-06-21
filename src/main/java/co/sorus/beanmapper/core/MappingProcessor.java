package co.sorus.beanmapper.core;

import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

public class MappingProcessor {

    private final ProcessingEnvironment env;

    private DeclaredType mappedBeanType;

    private DeclaredType mappedFromType;

    public MappingProcessor(ProcessingEnvironment processingEnv) {
        this.env = processingEnv;

        init();
    }

    private void init() {
        TypeElement element = env.getElementUtils().getTypeElement("co.sorus.beanmapper.MappedBean");
        mappedBeanType = env.getTypeUtils().getDeclaredType(element);

        element = env.getElementUtils().getTypeElement("co.sorus.beanmapper.MappedFrom");
        mappedFromType = env.getTypeUtils().getDeclaredType(element);
    }

    public BeanMapping process(TypeElement toElement) {
        BeanClass toBean = new BeanClass(toElement);
        BeanMapping mapping = new BeanMapping();
        mapping.to = toElement;

        // No need to proceed if error in bean classes
        if (!toBean.validate(BeanClass.Type.TO))
            return null;

        // Get FROM bean map info
        AnnotationMirror mappedBeanAnnotation = getAnnotation(toElement, mappedBeanType, true);
        TypeElement fromElement = getFromClass(mappedBeanAnnotation, "from");
        BeanClass fromBean = new BeanClass(fromElement);
        mapping.from = fromElement;

        // Get TO bean properties
        List<VariableElement> destProperties = toBean.getProperties();
        for (VariableElement property : destProperties) {
            BeanMapping.Property prop = new BeanMapping.Property();
            prop.to = property;
            AnnotationMirror annotation = getAnnotation(property, mappedFromType, false);
            prop.hasAnnotation = (annotation != null);

            if (annotation != null) {
                prop.value = (String) getMethodValue(annotation, "value");
                prop.mapper = (DeclaredType) getMethodValue(annotation, "using");
            }
            mapping.props.add(prop);
        }

        // Get FROM bean mapping
        for (BeanMapping.Property property : mapping.props) {
            if (!property.hasAnnotation)
                property.from = property.to;
        }

        return mapping;
    }

    private AnnotationMirror getAnnotation(Element element, DeclaredType type, boolean mandatory) {
        for (AnnotationMirror annotation : element.getAnnotationMirrors()) {
            if (env.getTypeUtils().isSameType(type, annotation.getAnnotationType()))
                return annotation;
        }
        return null;
    }

    private TypeElement getFromClass(AnnotationMirror annotation, String field) {
        Map<? extends ExecutableElement, ? extends AnnotationValue> map = annotation.getElementValues();
        for (ExecutableElement element : map.keySet()) {
            if (!element.getSimpleName().toString().equals(field))
                continue;

            DeclaredType sourceType = (DeclaredType) map.get(element).getValue();
            TypeElement sourceElement = (TypeElement) sourceType.asElement();
            return sourceElement;
        }
        return null;
    }

    private Object getMethodValue(AnnotationMirror annotation, String field) {
        Map<? extends ExecutableElement, ? extends AnnotationValue> map = annotation.getElementValues();
        for (ExecutableElement element : map.keySet()) {
            if (!element.getSimpleName().equals(field))
                continue;

            return map.get(element).getValue();
        }
        return null;
    }
}
