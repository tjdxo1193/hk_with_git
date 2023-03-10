
package lims.api.ts.vo;

import lims.api.common.domain.FileKey;
import lims.api.common.domain.UpdateDetect;
import lims.api.common.vo.ApproveVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TestResultInputVO implements UpdateDetect {
    private String plntCd;
    private Integer pitmSpecIdx;
    private String pitmTyp;
    private String pitmTypNm;
    private String pitmCd;
    private String ansNo;
    private String ispReqNo; //검사 요청 번호
    private String lotNo;
    private Integer assNo;
    private String pitmNm;
    private String aitmCd;
    private String aitmKn;
    private String ansProcCd;
    private String rstProcCd;
    private String reqDt;
    private List<String> searchReqDt;
    private List<String> searchRcpDt;
    private Integer reqIdx;
    private Integer ansFileIdx;
    private Integer fileIdx;
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
    private String clltRmk;
    private String clltUid;
    private String clltNm;
    private String clltDt;
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
    private String rjtUid;
    private String rjtNm;
    private String rjtDs;
    private String rjtRea;
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
    private Integer labEvtIdx;
    private Integer labEvtAprIdx;
    private Integer rstFileIdx;
    private String oosYn;
    private String batchNo;
    private String amitmCd;
    private String amitmNm;
    private String orderNo;
    private String phsOrderNo;
    private String pdtOrderNo;
    private String phsOrderItm; //구매 오더 항목
    private String ocrDt;
    private String crtvMsr;
    private String aprYn;
    private String revwUid;
    private String slvJdgCfm;
    private String slvJdgCfmNm;
    private String slvJdgNonCfm;
    private String slvJdgNonCfmNm;
    private List<String> TestProcessList;
    private List<TestResultInputVO> editedRowItems;
    private ApproveVO approveInfo;
    private List<MultipartFile> addedFiles = new ArrayList<>();
    private List<FileKey> removedFileIds = new ArrayList<>();
    private String fileCnt;
    private String vriaNo;
    private String vriaKn;
    private String crgDptCd;
    private String crgDptNm;
    private String sytJdg;
    private String mkrQty; //표시량
    private String aitmRmk; //시험항목 비고
    private String nonCfmRea; //부적합사유
    private String pitmSpcc; //품목 특이사항
    /*보류정보*/
    private String hldUid;
    private String hldRea;
}