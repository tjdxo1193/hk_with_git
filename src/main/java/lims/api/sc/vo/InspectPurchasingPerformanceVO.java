package lims.api.sc.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InspectPurchasingPerformanceVO {
    /* QT 품목 시험 의뢰 */
    private String plntCd;
    private Integer reqIdx;
    private String ispReqNo;
    private String ispReqDt;
    private String mtrCd;
    private String mtrNm;
    private String batchNo;
    private String etrQty;
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
    private String itmPrsCompNm;
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
    private String addCol5;
    private String udtDs;
    private String revDs;

    /* 별칭 */
    private String pitmCd;
    private String pitmNm;
    private List<String>ispReqDtList = new ArrayList<>();
    private String fiscalYr;
    private String month;
    private String pitmTypNm;
    private String ispScr;
    private String ispPfaCanlYn;
    private String ispPfaIfDs;
}
