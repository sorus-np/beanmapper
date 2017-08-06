package testFiles;

import co.sorus.beanmapper.MappedBean;
import co.sorus.beanmapper.Unmapped;

@MappedBean(from = DomainObject.class)
public class DTOUnmappedProperty {
    
    private long id;

    @Unmapped
    private String unmappedProperty;
    
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getUnmappedProperty () {
        return this.unmappedProperty;
    }
    
    public void setUnmappedProperty(String unmappedProperty) {
        this.unmappedProperty = unmappedProperty;
    }
}