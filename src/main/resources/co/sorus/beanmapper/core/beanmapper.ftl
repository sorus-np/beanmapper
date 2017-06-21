package ${packageName};

public class ${toBean}Generator implements co.sorus.beanmapper.BeanMapper<${fromBean}, ${toBean}> {

    @Override
    public ${toBean} map (${fromBean} _from) {
        ${toBean} _to = new ${toBean}();

        <#list properties as prop>
        try {
            ${prop.fromType} prop${prop.index} = _from.${prop.fromGetter}();
            _to.${prop.toSetter}(prop${prop.index});
        } catch (NullPointerException e) {
        }

        </#list>

        return _to;
    }
}
