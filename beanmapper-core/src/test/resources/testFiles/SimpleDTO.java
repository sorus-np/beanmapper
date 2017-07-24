package testFiles;

import co.sorus.beanmapper.MappedBean;

@MappedBean(from = DomainObject.class)
public class SimpleDTO {
    private long id;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}