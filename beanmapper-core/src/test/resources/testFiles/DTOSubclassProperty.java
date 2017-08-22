package testFiles;

import co.sorus.beanmapper.MappedBean;

@MappedBean(from = DomainSubclass.class)
public class DTOSubclassProperty {
    private String address;

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}