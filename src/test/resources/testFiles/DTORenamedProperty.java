package testFiles;

import co.sorus.beanmapper.MappedBean;
import co.sorus.beanmapper.MappedFrom;

@MappedBean(from = DomainObject.class)
public class DTORenamedProperty {

    @MappedFrom("name")
    private String firstname;

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}