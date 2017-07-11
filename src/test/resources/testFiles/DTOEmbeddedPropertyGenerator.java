package testFiles;

public class DTOEmbeddedPropertyGenerator implements co.sorus.beanmapper.BeanMapper<testFiles.DomainObject, DTOEmbeddedProperty> {

    @Override
    public DTOEmbeddedProperty map(testFiles.DomainObject _from) {
        DTOEmbeddedProperty _to = new DTOEmbeddedProperty();

        try {
            java.lang.String prop0 = _from.getEmbedded().getName();
            _to.setName(prop0);
        } catch (NullPointerException e) {
        }

        return _to;
    }
}
