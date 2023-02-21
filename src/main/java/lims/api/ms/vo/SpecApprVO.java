package lims.api.ms.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecApprVO implements UpdateDetect {
    private String plntCd;
    private String aitmSpecIdx;
    private String aitmSpecVer;
    private String pitmSpecIdx;
    private String pitmCd;
    private String pitmNm;
    private String pitmEn;
    private String crgDptCd;
    private String crgDptNm;
    private String pitmTyp;
    private String pitmTypNm;
    private String pitmVer;
    private String specProcCd;
    private String delYn;
    private String useVerYn;
    private String rvsNo;
    private String docNo;
    private String rvsDt;
    private String enfoDt;
    private String rvsReaCd;
    private String rvsCtt;
    private String rvsDivPs;
    private String revwUid;
    private String revwDs;
    private String revwIp;
    private Integer pitmAnsSpecAprIdx;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;
    private String aitmSeq;
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

    private String loginUserUid;
    private String ptimTypeNm;
    private String specProcNm;

    private String aprIp;
    private String aprUid;
    private String aprRea;

    private String pItemTypeCondition;
    private String revwUnm;
    private String rjtUnm;
    private String amitmNm;
    private String aitmKn;
    private String vriaNo;
    private String vriaKn;

    private String mkrQty;
    private String aitmRmk;
}
