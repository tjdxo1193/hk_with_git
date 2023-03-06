package lims.api.integration.vo.intergation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public abstract class InterfaceSystemDistinguishable {

    public boolean isSRM() {
        boolean isNotSRM = StringUtils.isEmpty(getPhsOrderNo());
        if (isNotSRM) {
            log.error("Empty field. 'phsOrderNo'");
        }
        return !isNotSRM;
    }

    public boolean isMES() {
        boolean isNotMES = StringUtils.isEmpty(getPdtOrderNo());
        if (isNotMES) {
            log.error("Empty field. 'pdtOrderNo'");
        }
        return !isNotMES;
    }

    abstract String getPdtOrderNo();
    abstract String getPhsOrderNo();
}