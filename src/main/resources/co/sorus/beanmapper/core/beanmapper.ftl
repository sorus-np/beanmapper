package ${packageName};

public class ${toBean}Generator implements co.sorus.beanmapper.BeanMapper<${fromBean}, ${toBean}> {

    @Override
    public ${toBean} map (${fromBean} _from) {
        ${toBean} _to = new ${toBean}();



        return _to;
    }
}
