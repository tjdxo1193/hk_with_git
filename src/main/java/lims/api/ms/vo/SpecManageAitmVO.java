package lims.api.ms.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpecManageAitmVO implements UpdateDetect {
    private String plntCd;
    private Integer aitmSpecIdx;
    private Integer aitmSeq;
    private String mkrQty;
    private String aitmRmk;
    private String ansDptCd;
    private String amitmCd;
    private String aitmOrd;
    private String perspecTxt;
    private String owcSpecTxt;
    private String specTxtEn;
    private String specTyp;
    private String jdgTyp;
    private String perSlvLow;
    private String perSlvUpp;
    private String perSlvDesc;
    private String owcSlvLow;
    private String owcSlvUpp;
    private String owcSlvDesc;
    private String slvJdgCfm;
    private String slvJdgNonCfm;
    private String rstUnitCd;
    private String rstDpnt;
    private String rptPrtSlvVal;
    private String rptPrtItmNm;
    private String rptPrtYn;
    private String ispDptCd;
    private String smpClltQty;
    private String smpClltUnit;
    private String ispDurTm;
    private String eqmUseTm;

    private String aitmKn;
    private String vriaNo;
    private String vriaKn;
    private String aitmEn;
    private String aitmAbbr;
    private String aitmCd;
    private String vriaEn;
    private String rmk;
}
