package lims.api.ts.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QT_PITM_ANS_REQ")
public class QtPitmAnsReq {
    @AuditId
    private String plntCd;
    @AuditId
    private Integer reqIdx;
    private String ispReqNo;
    private String ispReqDt;
    private String mtrCd;
    private String mtrNm;
    private String batchNo;
    private String etrNo;
    private String inpUnit;
    private String savePla;
    private String etrDt;
    private String phsOrderTyp;
    private String phsOrderNo;
    private String phsOrderItm;
    private String itmCtg;
    private String phsUnitPre;
    private String amtUnit;
    private String currCd;
    private String amtLoccurr;
    private String phsOrderQty;
    private String phsOrderUnit;
    private String splCd;
    private String splNm;
    private String phsCrgNm;
    private String dlvYn;
    private String vdrCtrtDt;
    private String vdrRsvTm;
    private String vdrRptRcpCrst;
    private String lotNo;
    private String repBomNo;
    private String splLotNo;
    private String makDt;
    private String strgLmt;
    private String useLmt;
    private String pdtOrderTyp;
    private String pdtOrderNo;
    private String itnPrsCompCd;
    private String otmPrsCompNm;
    private String makEqp;
    private String wrkNm;
    private String mtrDocNo;
    private String mtrDocYr;
    private String mtrDocItm;
    private String csmBpCd;
    private String csmNm;
    private String addCol1;
    private String addCol2;
    private String addCol3;
    private String addCol4;
    private String udtDs;
    private String revDs;
}
