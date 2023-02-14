package lims.api.integration.vo.intergation;

import org.apache.commons.lang3.StringUtils;

public abstract class InterfaceSystemDistinguishable {

    public boolean isSRM() {
        return StringUtils.isNotEmpty(getPhsOrderNo());
    }

    public boolean isMES() {
        return StringUtils.isNotEmpty(getPdtOrderNo());
    }

    abstract String getPdtOrderNo();
    abstract String getPhsOrderNo();
}