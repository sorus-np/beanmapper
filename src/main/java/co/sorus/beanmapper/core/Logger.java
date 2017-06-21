package co.sorus.beanmapper.core;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;

public class Logger {
    private static Messager mMessager;

    private Logger() {
    }

    public static void debug(final String text) {
        mMessager.printMessage(Kind.MANDATORY_WARNING, text);
    }

    public static void error(final String text) {
        mMessager.printMessage(Kind.MANDATORY_WARNING, text);
    }

    public static void error(final String text, final Element element) {
        mMessager.printMessage(Kind.ERROR, text, element);
    }

    public static void setMessager(final Messager messager) {
        mMessager = messager;
    }
}
