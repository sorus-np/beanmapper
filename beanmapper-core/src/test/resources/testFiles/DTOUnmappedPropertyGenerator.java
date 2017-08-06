package testFiles;

public class DTOUnmappedPropertyGenerator implements co.sorus.beanmapper.BeanMapper<testFiles.DomainObject, DTOUnmappedProperty> {

    @Override
    public DTOUnmappedProperty map(testFiles.DomainObject _from) {
        DTOUnmappedProperty _to = new DTOUnmappedProperty();

        try {
            long prop0 = _from.getId();
            _to.setId(prop0);
        } catch (NullPointerException e) {
        }

        return _to;
    }
}
