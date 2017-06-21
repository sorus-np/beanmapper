package testFiles;

import co.sorus.beanmapper.MappedBean;

@MappedBean(from = DomainObject.class)
public class DTO2Property {
    private long id;
    private int age;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}