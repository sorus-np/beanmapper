package testFiles;

public class DTOSubclassPropertyGenerator
        implements co.sorus.beanmapper.BeanMapper<testFiles.DomainSubclass, DTOSubclassProperty> {

    @Override
    public DTOSubclassProperty map(testFiles.DomainSubclass _from) {
        DTOSubclassProperty _to = new DTOSubclassProperty();

        try {
            java.lang.String prop0 = _from.getAddress();
            _to.setAddress(prop0);
        } catch (NullPointerException e) {
        }

        return _to;
    }
}
