package testFiles;

public class SimpleDTOGenerator implements co.sorus.beanmapper.BeanMapper<testFiles.DomainObject, SimpleDTO> {

    @Override
    public SimpleDTO map(testFiles.DomainObject _from) {
        SimpleDTO _to = new SimpleDTO();

        try {
            long prop0 = _from.getId();
            _to.setId(prop0);
        } catch (NullPointerException e) {
        }

        return _to;
    }
}
