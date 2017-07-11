package co.sorus.beanmapper.core;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

public class BeanClass {
    private final List<VariableElement> properties;
    private final List<ExecutableElement> methods;

    public BeanClass(final TypeElement root) {
        List<? extends Element> elements = root.getEnclosedElements();
        properties = ElementFilter.fieldsIn(elements);
        methods = ElementFilter.methodsIn(elements);
    }

    public boolean validate(Type type) {
        boolean validated = true;
        if (type == Type.TO || type == Type.BOTH)
            for (VariableElement variable : properties) {
                String name = variable.getSimpleName().toString();
                if (!hasMethod(accessor(name, AccessType.SETTER))) {
                    validated = false;
                    Logger.error(
                            String.format("Field %s does not have a setter method. Setter method is required.", name),
                            variable);
                }
            }
        return validated;
    }

    public List<VariableElement> getProperties() {
        return this.properties;
    }

    public boolean hasMethod(String name) {
        for (ExecutableElement el : methods) {
            if (el.getSimpleName().toString().equals(name))
                return true;
        }
        return false;
    }

    public ExecutableElement getMethod(String name) {
        for (ExecutableElement el : methods) {
            if (el.getSimpleName().toString().equals(name))
                return el;
        }
        return null;
    }

    public ExecutableElement getMethod(Element from, Element to) {
        String parameter = type(from);
        String returnType = type(to);

        for (ExecutableElement el : methods) {
            String actualReturnType = el.getReturnType().toString();
            if (!actualReturnType.equals(returnType))
                continue;

            List<? extends VariableElement> parameters = el.getParameters();
            if (parameters.size() != 1)
                continue;

            String actualParameter = parameters.get(0).asType().toString();
            if (actualParameter.equals(parameter))
                return el;
        }
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

    public boolean hasProperty(String name) {
        for (VariableElement el : properties) {
            if (el.getSimpleName().toString().equals(name))
                return true;
        }
        return false;
    }

    public String accessor(String name, AccessType accessType) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        name = accessType.type() + name;

        if (hasMethod(name))
            return name;
        else
            return null;
    }

    public enum AccessType {
        GETTER("get"),

        SETTER("set");

        private final String type;

        AccessType(String type) {
            this.type = type;
        }

        public String type() {
            return this.type;
        }
    }

    public enum Type {
        FROM,

        TO,

        BOTH
    }
}
