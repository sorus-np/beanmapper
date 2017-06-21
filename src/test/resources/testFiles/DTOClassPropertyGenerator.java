package testFiles;

public class DTOClassPropertyGenerator implements co.sorus.beanmapper.BeanMapper<testFiles.DomainObject, DTOClassProperty> {

    @Override
    public DTOClassProperty map(testFiles.DomainObject _from) {
        DTOClassProperty _to = new DTOClassProperty();

        try {
            java.lang.String prop0 = _from.getName();
            _to.setName(prop0);
        } catch (NullPointerException e) {
        }

        return _to;
    }
}
