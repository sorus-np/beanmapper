package testFiles;

public class DTORenamedPropertyGenerator implements co.sorus.beanmapper.BeanMapper<testFiles.DomainObject, DTORenamedProperty> {

    @Override
    public DTORenamedProperty map(testFiles.DomainObject _from) {
        DTORenamedProperty _to = new DTORenamedProperty();

        try {
            java.lang.String prop0 = _from.getName();
            _to.setFirstname(prop0);
        } catch (NullPointerException e) {
        }

        return _to;
    }
}
