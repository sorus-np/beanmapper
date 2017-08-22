package co.sorus.beanmapper.core;

import java.util.ArrayList;
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
import javax.lang.model.type.TypeMirror;

import co.sorus.beanmapper.core.BeanClass.AccessType;

public class MappingProcessor {

    private final ProcessingEnvironment env;

    private DeclaredType mappedBeanType;

    private DeclaredType mappedFromType;

    private DeclaredType unmappedType;

    private ImplicitConversions conversions;

    public MappingProcessor(ProcessingEnvironment processingEnv) {
        this.env = processingEnv;

        init();
    }

    private void init() {
        TypeElement element = env.getElementUtils().getTypeElement("co.sorus.beanmapper.MappedBean");
        mappedBeanType = env.getTypeUtils().getDeclaredType(element);

        element = env.getElementUtils().getTypeElement("co.sorus.beanmapper.MappedFrom");
        mappedFromType = env.getTypeUtils().getDeclaredType(element);

        element = env.getElementUtils().getTypeElement("co.sorus.beanmapper.Unmapped");
        unmappedType = env.getTypeUtils().getDeclaredType(element);

        this.conversions = new ImplicitConversions(env);
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

        // Get super class of from bean
        mapping.fromSuperClasses = new ArrayList<>();
        for (TypeMirror supertypes : env.getTypeUtils().directSupertypes(fromElement.asType())) {
            DeclaredType declaredType = (DeclaredType) supertypes;
            TypeElement supertypeElement = (TypeElement) declaredType.asElement();
            mapping.fromSuperClasses.add(supertypeElement);
        }

        // Get TO bean properties
        getDestinationProperties(toBean.getProperties(), mapping);

        // Get FROM bean mapping
        for (BeanMapping.Property property : mapping.props)
            getPropertyMapping(property, mapping.from, fromBean, mapping.fromSuperClasses);

        return mapping;
    }

    private ExecutableElement getExplicitMapping(BeanMapping.Property property, TypeElement source,
            BeanClass fromBean) {
        if (fromBean.hasProperty(property.value)) {
            String getter = fromBean.accessor(property.value, BeanClass.AccessType.GETTER);
            return fromBean.getMethod(getter);
        }
        return null;
    }

    private void getPropertyMapping(BeanMapping.Property property, TypeElement source, BeanClass fromBean,
            List<TypeElement> superTypes) {
        if (isComplexMapping(property.value)) {
            property.isComplex = true;
            property.methods = handleComplexMapper(property.value, source);

            if (property.mapperClass != null) {
                BeanClass mapperBean = new BeanClass(property.mapperClass);
                Element lastElement = property.methods.get(property.methods.size() - 1);
                property.mapperMethod = mapperBean.getMethod(lastElement, property.to);
            }
            return;
        }

        property.isComplex = false;

        ExecutableElement mappedFrom = getExplicitMapping(property, source, fromBean);
        for (TypeElement superType : superTypes) {
            if (mappedFrom != null)
                break;

            // Look for property in super classes
            BeanClass superBean = new BeanClass(superType);
            mappedFrom = getExplicitMapping(property, source, superBean);
        }

        if (mappedFrom != null)
            property.from = mappedFrom;

        if (property.mapperClass != null) {
            BeanClass mapperBean = new BeanClass(property.mapperClass);
            property.mapperMethod = mapperBean.getMethod(property.from, property.to);
        }

        // If type do not match, try implicit conversion
        ExecutableElement method = (property.mapperMethod == null ? property.from : property.mapperMethod);
        if (method != null) {
            String actualType = method.getReturnType().toString();
            String desiredType = property.to.asType().toString();
            property.implicitConversion = false;
            if (!actualType.equals(desiredType)) {
                if (conversions.attemptConversion(method.getReturnType(), property.to.asType()))
                    property.implicitConversion = true;
            }
        }
    }

    private boolean isComplexMapping(String value) {
        return value.indexOf(".") > -1;
    }

    private void getDestinationProperties(List<VariableElement> destProperties, BeanMapping mapping) {
        // List<VariableElement> destProperties = toBean.getProperties();
        for (VariableElement property : destProperties) {

            // if unmapped, do not generate mapping
            if (getAnnotation(property, unmappedType, false) != null) {
                continue;
            }

            BeanMapping.Property prop = new BeanMapping.Property();
            prop.to = property;
            AnnotationMirror annotation = getAnnotation(property, mappedFromType, false);
            prop.hasAnnotation = (annotation != null);

            prop.value = prop.to.toString();
            if (annotation != null) {
                String value = (String) getMethodValue(annotation, "value");
                if (value != null)
                    prop.value = value;
                DeclaredType type = (DeclaredType) getMethodValue(annotation, "using");
                if (type != null)
                    prop.mapperClass = (TypeElement) type.asElement();
            }
            mapping.props.add(prop);
        }
    }

    private List<Element> handleComplexMapper(String value, TypeElement source) {

        List<Element> froms = new ArrayList<>();
        for (String mapper : value.split("\\.")) {
            BeanClass bean = new BeanClass(source);

            if (bean.hasProperty(mapper)) {
                String getter = bean.accessor(mapper, AccessType.GETTER);
                if (bean.hasMethod(getter)) {
                    ExecutableElement method = bean.getMethod(getter);
                    froms.add(method);
                    TypeMirror typeMirror = method.getReturnType();
                    if (typeMirror.getKind().isPrimitive()) {
                        // TODO handle primitive
                    } else {
                        DeclaredType declaredType = (DeclaredType) typeMirror;
                        source = (TypeElement) declaredType.asElement();
                    }
                } else {
                    // TODO - handle property
                }

            }
        }

        return froms;
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
            if (!element.getSimpleName().toString().equals(field))
                continue;

            return map.get(element).getValue();
        }
        return null;
    }
}
