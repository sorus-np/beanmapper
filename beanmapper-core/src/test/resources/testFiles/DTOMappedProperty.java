package testFiles;

import co.sorus.beanmapper.MappedBean;
import co.sorus.beanmapper.MappedFrom;

@MappedBean(from = DomainObject.class)
public class DTOMappedProperty {

    @MappedFrom(using = DTOMappedProperty.class)
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String capitalize(String str) {
        return str.toUpperCase();
    }
}