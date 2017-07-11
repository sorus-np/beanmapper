package testFiles;

import co.sorus.beanmapper.MappedBean;
import co.sorus.beanmapper.MappedFrom;

@MappedBean(from = DomainObject.class)
public class DTOEmbeddedMappedProperty {

    @MappedFrom(value = "embedded.name", using = DTOEmbeddedMappedProperty.class)
    private int length;

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int map(String name) {
        return name.length();
    }
}