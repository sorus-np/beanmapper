package co.sorus.beanmapper.core;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import co.sorus.beanmapper.MappedBean;

@SupportedAnnotationTypes("co.sorus.beanmapper.MappedBean")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MappedBeanProcessor extends AbstractProcessor {

    @Override
    public final synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        Logger.setMessager(processingEnv.getMessager());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(MappedBean.class);

        MappingProcessor processor = new MappingProcessor(processingEnv);
        MapFileGenerator generator = new MapFileGenerator(processingEnv);

        for (TypeElement element : ElementFilter.typesIn(annotatedElements)) {
            BeanMapping mapping = processor.process(element);
            generator.generate(mapping);
        }

        return false;
    }

}
