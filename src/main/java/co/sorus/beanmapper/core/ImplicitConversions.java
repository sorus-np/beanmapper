package co.sorus.beanmapper.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class ImplicitConversions {

    private final static List<PossibleConversion> conversions = new ArrayList<>();

    static {
        conversions.add(new PossibleConversion("java.lang.Enum", "java.lang.String"));
    }

    private Elements elementUtils;
    private Types typeUtils;

    public ImplicitConversions(ProcessingEnvironment env) {
        elementUtils = env.getElementUtils();
        typeUtils = env.getTypeUtils();
    }

    public boolean attemptConversion(TypeMirror from, TypeMirror to) {
        for (PossibleConversion conversion : conversions) {
            TypeMirror fromType = elementUtils.getTypeElement(conversion.from).asType();
            boolean fromCompatible = typeUtils.isAssignable(from, fromType);

            TypeMirror toType = elementUtils.getTypeElement(conversion.to).asType();
            boolean toCompatible = typeUtils.isAssignable(to, toType);

            if (toCompatible)
                return true;
        }

        return false;
    }

    private static class PossibleConversion {
        protected String from;
        protected String to;

        public PossibleConversion(String from, String to) {
            this.from = from;
            this.to = to;
        }
    }
}
