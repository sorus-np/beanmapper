package co.sorus.beanmapper.core;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

public final class BeanMapping {

    protected TypeElement from;

    protected TypeElement to;

    protected List<Property> props = new ArrayList<>();

    public static class Property {
        protected Element to;

        protected Element from;

        protected boolean hasAnnotation;

        protected String value;

        protected DeclaredType mapper;

    }
}
