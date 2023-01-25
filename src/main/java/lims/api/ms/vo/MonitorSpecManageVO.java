package lims.api.ms.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorSpecManageVO implements UpdateDetect {
    //MM_MITM
    private String mitmWrkPlcDiv;
    private String mitmPitmDiv;
    private String point;
    private String roomNo;
    private String grade;
    private String ansStrDt;
    private String ansCylCd;
    private String crgDptCd;
    private String perSpec;
    private String revwDurTm;
    private String aprDurTm;
    private String useYn;
    private String gradeNm;
    private String ansCylNm;
    private String crgDptNm;
    private String specProcNm;
    private String mitmWrkPlcDivNm;
    private String mitmPitmDivNm;

    // MM_MITM_SPEC
    private String plntCd;
    private int mitmSpecIdx;
    private String mitmCd;
    private int aitmSpecIdx;
    private String specProcCd;
    private String delYn;
    private String useVerYn;
    private String rvsNo;
    private String docNo;
    private String rvsDt;
    private String enfoDt;
    private String rvsReaCd;
    private String rvsReaNm;
    private String rvsCtt;
    private String rvsDivPs;
    private String revwUid;
    private String revwDs;
    private String revwIp;
    private int mitmSpecAprIdx;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;

    // MM_MITM_AITM_SPEC
    private Integer aitmSpecVer;

    // MM_MITM_SPEC_AITM
    private int aitmSeq;
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

    //modal - ms_aitm , ms_amitm
    private String aitmCd;
    private String aitmKn;
     private String aitmEn;
     private String aitmAbbr;
     private String vriaNo;
     private String vriaKn;
     private String vriaEn;
     private String rmk;
     private String aprUid;
     private String aprReqUid;

     private String revwUnm;
     private String rjtUnm;
}
