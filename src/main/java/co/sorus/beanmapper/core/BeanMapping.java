package co.sorus.beanmapper.core;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public final class BeanMapping {

    protected TypeElement from;

    protected TypeElement to;

    protected List<Property> props = new ArrayList<>();

    public static class Property {
        protected VariableElement to;

        protected ExecutableElement from;

        protected boolean hasAnnotation;

        protected String value;

        protected TypeElement mapperClass;
        protected ExecutableElement mapperMethod;

        protected boolean implicitConversion = false;

    }
}
