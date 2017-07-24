package testFiles;

public class DTOEnumPropertyGenerator implements co.sorus.beanmapper.BeanMapper<testFiles.DomainObject, DTOEnumProperty> {

    @Override
    public DTOEnumProperty map(testFiles.DomainObject _from) {
        DTOEnumProperty _to = new DTOEnumProperty();

        try {
            java.time.DayOfWeek prop0 = _from.getDay();
            _to.setDay(prop0.toString());
        } catch (NullPointerException e) {
        }

        return _to;
    }
}
