package lims.api.ms.vo;

import io.micrometer.core.lang.Nullable;
import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MonitorSpecApprRequestVO implements UpdateDetect {
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
    private String rvsCtt;
    private String rvsDivPs;
    private String revwUid;
    private String revwDs;
    private String revwIp;
    private Integer mitmSpecAprIdx;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;

    // MM_MITM_AITM_SPEC
    private int aitmSpecVer;

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
    private String aprReqRea;
    private String aprUid;

    private String aitmKn;
    private String vriaNo;
    private String vriaKn;
}
