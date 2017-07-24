package co.sorus.beanmapper.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Processor;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class CommandLineUtility {

    private static String[] filesToCompile = { "testFiles/DomainObject.java", "testFiles/EmbeddedObject.java",
            "testFiles/SimpleDTO.java", "testFiles/DTO2Property.java", "testFiles/DTOClassProperty.java",
            "testFiles/DTORenamedProperty.java", "testFiles/DTOMappedProperty.java", "testFiles/DTOEnumProperty.java",
            "testFiles/DTOEmbeddedProperty.java", "testFiles/DTOEmbeddedMappedProperty.java" };

    public static void main(String[] args) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        List<File> files = new ArrayList<>();
        ClassLoader loader = CommandLineUtility.class.getClassLoader();
        for (String filename : filesToCompile)
            files.add(new File(loader.getResource(filename).toURI()));

        Iterable<? extends JavaFileObject> fileObjects = fileManager.getJavaFileObjectsFromFiles(files);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, fileObjects);

        List<Processor> processors = new ArrayList<>();
        processors.add(new MappedBeanProcessor());

        task.setProcessors(processors);
        task.call();
        fileManager.close();
    }

}
