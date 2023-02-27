package lims.api.np.vo;

import lims.api.common.domain.UpdateDetect;
import lims.api.common.vo.ApproveVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PreventRecurrenceReviewVO implements UpdateDetect {
    private String plntCd; //사업장 코드
    private Integer pitmSpecIdx;
    private String pitmTyp;
    private String pitmTypNm;
    private String pitmCd;
    private String ansNo;
    private String lotNo;
    private Integer assNo;
    private String pitmNm;
    private String aitmCd;
    private String aitmKn;
    private String ansProcCd;
    private String rstProcCd;
    private String reqDt;
    private List<String> searchReqDt;
    private Integer reqIdx;
    private Integer ansFileIdx;
    private String rcpDt;
    private String emgYn;
    private String perspecTxt;
    private String reqRmk;
    private String rcpRmk;
    private String assSpcc;
    private String makDt;
    private String etrDt;
    private String splLotNo;
    private String useLmt;
    private String cplRqmDt;
    private String assDt;
    private String splCd;
    private String splNm;
    private String reqUid;
    private String reqNm;
    private String reqDptCd;
    private String reqDptNm;
    private String etrQty;
    private String inpUnit;
    private Integer ansIdx;
    private Integer rstSeq;
    private String ansDptCd;
    private String ansDptNm;
    private String ansUid;
    private String ansNm;
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
    private String oosYn;
    private String sytJdg;
    private String sytJdgNm;
    private String pitmEn;
    private Integer rstAprReqIdx;
    private String slvJdgCfm;
    private String slvJdgCfmNm;
    private String slvJdgNonCfm;
    private String slvJdgNonCfmNm;
    private Integer fileIdx;
    private String fileCnt;
    private String vriaNo;
    private String vriaKn;
    private String crgDptCd;
    private String crgDptNm;
    private String ispReqNo;
    private String batchNo;
    private String orderNo;
    private String revwCmmt; //검토의견
    private String nonCfmRea; //부적합사유
    private String nonCfmRev; //부적합수신
    private String nonCfmRef; //부적합참조
    private String nonCfmEtc; //부적합기타
    private String nonCfmProcCd;
    private List<String> processList;
    private String pitmSpcc; //품목 특이사항
    private String smpVolTot; //총검체량
    private String reoPrevRpyCtt;
    private String userId;

    private String pdtOrderNo; //생산 오더 번호
    private String phsOrderNo; //구매 오더 번호
    private String phsOrderItm; //구매 오더 항목

    /*승인정보*/
    private ApproveVO approveInfo;
    private Integer reoPrevAprReqIdx;

    private Integer rptCnt;
}
