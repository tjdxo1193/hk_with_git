package lims.api.ms.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestTermManageVO implements UpdateDetect {
    private String plntCd;
    private String ansTrmCd;
    private String ansTrmMarkNm;
    private String ansCylDiv;
    private String ansCylDivNm;
    private String ansCylDivEn;
    private String ansCylDivAbbr;
    private int ansTrm;
    private int ansItv;
    private String useYn;
    private String delYn;
    private String trmOrd;
}