package lims.api.ms.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestCycleVO implements UpdateDetect {
    private String plntCd;
    private String ansCylCd;
    private String ansCylRuleNm;
    private String ansCylDivNm;
    private String ansCylMarkNm;
    private String ansCylDiv;
    private String ansItv;
    private String delYn;
    private String cylOrd;
}