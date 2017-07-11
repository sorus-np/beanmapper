package testFiles;

public class DTOEmbeddedMappedPropertyGenerator
        implements co.sorus.beanmapper.BeanMapper<testFiles.DomainObject, DTOEmbeddedMappedProperty> {

    @Override
    public DTOEmbeddedMappedProperty map(testFiles.DomainObject _from) {
        DTOEmbeddedMappedProperty _to = new DTOEmbeddedMappedProperty();

        try {
            java.lang.String prop0 = _from.getEmbedded().getName();
            testFiles.DTOEmbeddedMappedProperty mapper0 = new testFiles.DTOEmbeddedMappedProperty();
            _to.setLength(mapper0.map(prop0));
        } catch (NullPointerException e) {
        }

        return _to;
    }
}
