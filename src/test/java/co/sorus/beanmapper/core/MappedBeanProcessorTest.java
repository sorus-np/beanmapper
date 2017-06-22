package co.sorus.beanmapper.core;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

import javax.tools.JavaFileObject;

import org.junit.Test;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;

public class MappedBeanProcessorTest {

    JavaFileObject domainObject = JavaFileObjects.forResource("testFiles/DomainObject.java");

    @Test
    public void testOnePrimitiveProperty() {
        JavaFileObject dto = JavaFileObjects.forResource("testFiles/SimpleDTO.java");
        JavaFileObject generated = JavaFileObjects.forResource("testFiles/SimpleDTOGenerator.java");

        Compilation compilation = javac().withProcessors(new MappedBeanProcessor()).compile(dto, domainObject);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(compilation).generatedSourceFile("testFiles.SimpleDTOGenerator").hasSourceEquivalentTo(generated);
    }

    @Test
    public void testTwoPrimitiveProperty() {
        JavaFileObject dto = JavaFileObjects.forResource("testFiles/DTO2Property.java");
        JavaFileObject generated = JavaFileObjects.forResource("testFiles/DTO2PropertyGenerator.java");

        Compilation compilation = javac().withProcessors(new MappedBeanProcessor()).compile(dto, domainObject);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(compilation).generatedSourceFile("testFiles.DTO2PropertyGenerator").hasSourceEquivalentTo(generated);
    }

    @Test
    public void testClassProperty() {
        JavaFileObject dto = JavaFileObjects.forResource("testFiles/DTOClassProperty.java");
        JavaFileObject generated = JavaFileObjects.forResource("testFiles/DTOClassPropertyGenerator.java");

        Compilation compilation = javac().withProcessors(new MappedBeanProcessor()).compile(dto, domainObject);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(compilation).generatedSourceFile("testFiles.DTOClassPropertyGenerator")
                .hasSourceEquivalentTo(generated);
    }

    @Test
    public void testRenamedProperty() {
        JavaFileObject dto = JavaFileObjects.forResource("testFiles/DTORenamedProperty.java");
        JavaFileObject generated = JavaFileObjects.forResource("testFiles/DTORenamedPropertyGenerator.java");

        Compilation compilation = javac().withProcessors(new MappedBeanProcessor()).compile(dto, domainObject);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(compilation).generatedSourceFile("testFiles.DTORenamedPropertyGenerator")
                .hasSourceEquivalentTo(generated);
    }

    @Test
    public void testMappedProperty() {
        JavaFileObject dto = JavaFileObjects.forResource("testFiles/DTOMappedProperty.java");
        JavaFileObject generated = JavaFileObjects.forResource("testFiles/DTOMappedPropertyGenerator.java");

        Compilation compilation = javac().withProcessors(new MappedBeanProcessor()).compile(dto, domainObject);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(compilation).generatedSourceFile("testFiles.DTOMappedPropertyGenerator")
                .hasSourceEquivalentTo(generated);
    }

    @Test
    public void testEnum2StringProperty() {
        JavaFileObject dto = JavaFileObjects.forResource("testFiles/DTOEnumProperty.java");
        JavaFileObject generated = JavaFileObjects.forResource("testFiles/DTOEnumPropertyGenerator.java");

        Compilation compilation = javac().withProcessors(new MappedBeanProcessor()).compile(dto, domainObject);

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(compilation).generatedSourceFile("testFiles.DTOEnumPropertyGenerator")
                .hasSourceEquivalentTo(generated);
    }

}
