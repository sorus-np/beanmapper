package testFiles;

import co.sorus.beanmapper.MappedBean;

@MappedBean(from = DomainObject.class)
public class DTOClassProperty {
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}