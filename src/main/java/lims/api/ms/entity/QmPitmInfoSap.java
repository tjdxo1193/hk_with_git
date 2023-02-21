package lims.api.ms.entity;

import spring.audit.annotation.AuditEntity;
import spring.audit.annotation.AuditId;

@AuditEntity(name = "QM_PITM_INFO_SAP")
public class QmPitmInfoSap {
    @AuditId
    private String plntCd;
    @AuditId
    private String pitmCd;
    @AuditId
    private Integer pitmVer;
    private String mtrTyp;
    private String mtrMrp;
    private String labNo;
    private String brdAbbr;
    private String brdLne;
    private String nomMtr;
    private String etrCtnQty;
    private String rmtrSpec;
    private String nbr;
    private Character ftnYn;
    private String pcs01;
    private String pcs02;
    private String pcs03;
    private String pcs04;
    private String useTrm;
    private String otcPrd;
    private Character dmsEptYn;
    private String pearDiv;
    private String mkrVol;
    private String mkrVolUnit;
    private String busCrg;
    private String etnAnsReq;
    private String ctrptNo;
    private String perNo;
    private String chagVol;
    private String chagVolUnit;
    private String dioYn;
    private String prbInYn;
    private String prbFeYn;
    private String pnxFeYn;
    private String rmtrCfmul;
    private String nomUnit;
//    private String qtcPrd;
//    private String chagColUnit;
}
