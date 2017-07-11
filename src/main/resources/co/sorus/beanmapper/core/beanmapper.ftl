package ${packageName};

public class ${toBean}Generator implements co.sorus.beanmapper.BeanMapper<${fromBean}, ${toBean}> {

    @Override
    public ${toBean} map (${fromBean} _from) {
        ${toBean} _to = new ${toBean}();

        <#list properties as prop>
        try {
            <#if prop.complex == "true">
            ${prop.fromType} prop${prop.index} = _from.${prop.fromGetter};
            <#else>
            ${prop.fromType} prop${prop.index} = _from.${prop.fromGetter}();
            </#if>

            <#if prop.mapperUsed == "true">
            ${prop.mapperClass} mapper${prop.index} = new ${prop.mapperClass}();
            _to.${prop.toSetter}( mapper${prop.index}.${prop.mapperMethod}(prop${prop.index}) );
            <#else>
            _to.${prop.toSetter}(prop${prop.index}<#if prop.mapperAdditional == "true">.toString()</#if>);
            </#if>

        } catch (NullPointerException e) {
        }

        </#list>

        return _to;
    }
}
