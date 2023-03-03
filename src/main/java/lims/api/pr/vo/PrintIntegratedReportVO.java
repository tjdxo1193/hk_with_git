package lims.api.pr.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrintIntegratedReportVO {
    private String plntCd;
    private int reqIdx;
    private String ispReqNo;
    private String ispReqDt;
    private String mtrCd;
    private String mtrNm;
    private String batchNo;
    private String etrQty;
    private String inpUnit;
    private String etrDt;
    private String splCd;
    private String splNm;
    private String lotNo;
    private String splLotNo;
    private String makDt;
    private String csmNm;
    private String udtDs;
    private String revDs;
    private String pitmCd;
    private String pitmNm;
    private String pitmEn;
    private String pitmTyp;
    private String pitmTypNm;
    private String pitmVer;
    private Integer ansIdx;
    private String oosYn;
    private String ansProcCd;
    private String ansNo;
    private String sytJdg;
    private String sytJdgNm;
    private String reqDt;
    private String rcpDt;
    private String rcpUid;
    private String rcpUnm;
    private String clltDt;
    private String clltUid;
    private String clltUnm;
    private String smpStrgMtd;
    private String smpVolUnit;
    private String smpVolTot;
    private List<String> searchReqDt; //의뢰일자

    private String pkgMtrSpec;
    private String rmtrSpec;
    private String rmtrCfmul;
    private String rptSpcc;
    private String arptSpcc;


    private String aitmCd;
    private String aitmKn;
    private Integer rstSeq;
    private String ansDptCd;
    private String ansDptNm;
    private String ansUid;
    private String ansNm;
    private String perspecTxt;
    private String owcSpecTxt;
    private String specTyp;
    private String specTypNm;
    private String jdgTyp;
    private String jdgTypNm;
    private String rstVal;
    private String markVal;
    private String rstUnitCd;
    private String rstUnitNm;
    private String rptPrtItmNm;
    private String rstJdg;
    private String rstJdgNm;
    private String rptPrtSlvVal;
    private String rstRmk;
    private String ansRstInpDs;
    private Integer rstDpnt;
    private Integer perSlvLow;
    private Integer perSlvUpp;
    private String perSlvDesc;
    private Integer owcSlvLow;
    private Integer owcSlvUpp;
    private String owcSlvDesc;
    private String rptPrtYn;
    private Integer rstFileIdx;
    private Integer rstAprReqIdx;
    private String slvJdgCfm;
    private String slvJdgCfmNm;
    private String slvJdgNonCfm;
    private String slvJdgNonCfmNm;
    private String vriaNo;
    private String vriaKn;
    private String crgDptCd;
    private String crgDptNm;
    private String mkrQty; //표시량
    private String aitmRmk; //시험항목 비고

    /* 보고서 */
    private Integer arptSeq;
    private Integer arptRptIdx;
    private String shiptDt;
    private Integer shiptQty;
    private String wrtUid;
    private String wrtDs;
    private String prtUid;
    private String prtDs;
    private Character delYn;

    private Integer rptIdx;
    private String rptDiv;
    private String rptNm;
    private String rptRdPath;
    private String rptRmk;
    private String etcRmk;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtUid;

    /* 별칭 */
    private String wrtNm;
    private String prtNm;
    private String rptDivNm;
}
