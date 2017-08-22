package co.sorus.beanmapper.core;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

import javax.tools.JavaFileObject;

import org.junit.Test;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;

public class MappedBeanProcessorTest {

    JavaFileObject domainObject = JavaFileObjects.forResource("testFiles/DomainObject.java");
    JavaFileObject domainSubclass = JavaFileObjects.forResource("testFiles/DomainSubclass.java");
    JavaFileObject embeddedObject = JavaFileObjects.forResource("testFiles/EmbeddedObject.java");

    @Test
    public void testOnePrimitiveProperty() {
        JavaFileObject dto = JavaFileObjects.forResource("testFiles/SimpleDTO.java");
        JavaFileObject generated = JavaFileObjects.forResource("testFiles/SimpleDTOGenerator.java");

        Compilation compilation = javac().withProcessors(new MappedBeanProcessor()).compile(dto, domainObject,
                embeddedObject);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(compilation).generatedSourceFile("testFiles.SimpleDTOGenerator").hasSourceEquivalentTo(generated);
    }

    @Test
    public void testTwoPrimitiveProperty() {
        JavaFileObject dto = JavaFileObjects.forResource("testFiles/DTO2Property.java");
        JavaFileObject generated = JavaFileObjects.forResource("testFiles/DTO2PropertyGenerator.java");

        Compilation compilation = javac().withProcessors(new MappedBeanProcessor()).compile(dto, domainObject,
                embeddedObject);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(compilation).generatedSourceFile("testFiles.DTO2PropertyGenerator").hasSourceEquivalentTo(generated);
    }

    @Test
    public void testClassProperty() {
        JavaFileObject dto = JavaFileObjects.forResource("testFiles/DTOClassProperty.java");
        JavaFileObject generated = JavaFileObjects.forResource("testFiles/DTOClassPropertyGenerator.java");

        Compilation compilation = javac().withProcessors(new MappedBeanProcessor()).compile(dto, domainObject,
                embeddedObject);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(compilation).generatedSourceFile("testFiles.DTOClassPropertyGenerator")
                .hasSourceEquivalentTo(generated);
    }

    @Test
    public void testRenamedProperty() {
        JavaFileObject dto = JavaFileObjects.forResource("testFiles/DTORenamedProperty.java");
        JavaFileObject generated = JavaFileObjects.forResource("testFiles/DTORenamedPropertyGenerator.java");

        Compilation compilation = javac().withProcessors(new MappedBeanProcessor()).compile(dto, domainObject,
                embeddedObject);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(compilation).generatedSourceFile("testFiles.DTORenamedPropertyGenerator")
                .hasSourceEquivalentTo(generated);
    }

    @Test
    public void testMappedProperty() {
        JavaFileObject dto = JavaFileObjects.forResource("testFiles/DTOMappedProperty.java");
        JavaFileObject generated = JavaFileObjects.forResource("testFiles/DTOMappedPropertyGenerator.java");

        Compilation compilation = javac().withProcessors(new MappedBeanProcessor()).compile(dto, domainObject,
                embeddedObject);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(compilation).generatedSourceFile("testFiles.DTOMappedPropertyGenerator")
                .hasSourceEquivalentTo(generated);
    }

    @Test
    public void testEnum2StringProperty() {
        JavaFileObject dto = JavaFileObjects.forResource("testFiles/DTOEnumProperty.java");
        JavaFileObject generated = JavaFileObjects.forResource("testFiles/DTOEnumPropertyGenerator.java");

        Compilation compilation = javac().withProcessors(new MappedBeanProcessor()).compile(dto, domainObject,
                embeddedObject);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(compilation).generatedSourceFile("testFiles.DTOEnumPropertyGenerator")
                .hasSourceEquivalentTo(generated);
    }

    @Test
    public void testEmbeddedProperty() {
        JavaFileObject dto = JavaFileObjects.forResource("testFiles/DTOEmbeddedProperty.java");
        JavaFileObject generated = JavaFileObjects.forResource("testFiles/DTOEmbeddedPropertyGenerator.java");

        Compilation compilation = javac().withProcessors(new MappedBeanProcessor()).compile(dto, domainObject,
                embeddedObject);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(compilation).generatedSourceFile("testFiles.DTOEmbeddedPropertyGenerator")
                .hasSourceEquivalentTo(generated);
    }

    @Test
    public void testEmbeddedMappedProperty() {
        JavaFileObject dto = JavaFileObjects.forResource("testFiles/DTOEmbeddedMappedProperty.java");
        JavaFileObject generated = JavaFileObjects.forResource("testFiles/DTOEmbeddedMappedPropertyGenerator.java");

        Compilation compilation = javac().withProcessors(new MappedBeanProcessor()).compile(dto, domainObject,
                embeddedObject);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(compilation).generatedSourceFile("testFiles.DTOEmbeddedMappedPropertyGenerator")
                .hasSourceEquivalentTo(generated);
    }

    @Test
    public void testUnmappedProperty() {
        JavaFileObject dto = JavaFileObjects.forResource("testFiles/DTOUnmappedProperty.java");
        JavaFileObject generated = JavaFileObjects.forResource("testFiles/DTOUnmappedPropertyGenerator.java");

        Compilation compilation = javac().withProcessors(new MappedBeanProcessor()).compile(dto, domainObject,
                embeddedObject);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(compilation).generatedSourceFile("testFiles.DTOUnmappedPropertyGenerator")
                .hasSourceEquivalentTo(generated);
    }

    @Test
    public void testSubclassProperty() {
        JavaFileObject dto = JavaFileObjects.forResource("testFiles/DTOSubclassProperty.java");
        JavaFileObject generated = JavaFileObjects.forResource("testFiles/DTOSubclassPropertyGenerator.java");

        Compilation compilation = javac().withProcessors(new MappedBeanProcessor()).compile(dto, domainObject,
                domainSubclass, embeddedObject);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(compilation).generatedSourceFile("testFiles.DTOSubclassPropertyGenerator")
                .hasSourceEquivalentTo(generated);
    }
}
