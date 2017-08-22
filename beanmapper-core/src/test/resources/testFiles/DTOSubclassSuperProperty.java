package testFiles;

import co.sorus.beanmapper.MappedBean;

@MappedBean(from = DomainSubclass.class)
public class DTOSubclassSuperProperty {

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}