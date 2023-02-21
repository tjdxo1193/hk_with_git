package lims.api.ms.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestTermInTerValManageVO implements UpdateDetect {
    private String plntCd;
    private String ansTrmCd;
    private int itvSeq;
    private String accRulNm;
    private String accMarkNm;
    private int ansAccVal;
}
