package testFiles;

public class DTO2PropertyGenerator implements co.sorus.beanmapper.BeanMapper<testFiles.DomainObject, DTO2Property> {

    @Override
    public DTO2Property map(testFiles.DomainObject _from) {
        DTO2Property _to = new DTO2Property();

        try {
            long prop0 = _from.getId();
            _to.setId(prop0);
        } catch (NullPointerException e) {
        }

        try {
            int prop1 = _from.getAge();
            _to.setAge(prop1);
        } catch (NullPointerException e) {
        }

        return _to;
    }
}
