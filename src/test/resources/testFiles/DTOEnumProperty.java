package testFiles;

import co.sorus.beanmapper.MappedBean;

@MappedBean(from = DomainObject.class)
public class DTOEnumProperty {
    private String day;

    public String getDay() {
        return this.day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}