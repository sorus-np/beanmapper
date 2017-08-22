package testFiles;

public class DTOSubclassSuperPropertyGenerator
        implements co.sorus.beanmapper.BeanMapper<testFiles.DomainSubclass, DTOSubclassSuperProperty> {

    @Override
    public DTOSubclassSuperProperty map(testFiles.DomainSubclass _from) {
        DTOSubclassSuperProperty _to = new DTOSubclassSuperProperty();

        try {
            java.lang.String prop0 = _from.getName();
            _to.setName(prop0);
        } catch (NullPointerException e) {
        }

        return _to;
    }
}
