package co.sorus.beanmapper.core;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public final class BeanMapping {

    protected TypeElement from;

    protected TypeElement to;

    protected List<Property> props = new ArrayList<>();

    public static class Property {

        /**
         * This points to the property field in the destination bean.
         */
        protected VariableElement to;

        protected boolean isComplex;

        protected List<Element> methods;

        /**
         * This points to the getter method in the source bean.
         */
        protected ExecutableElement from;

        protected boolean hasAnnotation;

        protected String value;

        protected TypeElement mapperClass;
        protected ExecutableElement mapperMethod;

        protected boolean implicitConversion = false;

    }
}
