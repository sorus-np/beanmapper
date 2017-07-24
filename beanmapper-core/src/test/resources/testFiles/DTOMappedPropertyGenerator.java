package testFiles;

public class DTOMappedPropertyGenerator
        implements co.sorus.beanmapper.BeanMapper<testFiles.DomainObject, DTOMappedProperty> {

    @Override
    public DTOMappedProperty map(testFiles.DomainObject _from) {
        DTOMappedProperty _to = new DTOMappedProperty();

        try {
            java.lang.String prop0 = _from.getName();
            testFiles.DTOMappedProperty mapper0 = new testFiles.DTOMappedProperty();
            _to.setName(mapper0.capitalize(prop0));
        } catch (NullPointerException e) {
        }

        return _to;
    }
}
