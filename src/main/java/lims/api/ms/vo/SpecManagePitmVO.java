package lims.api.ms.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecManagePitmVO implements UpdateDetect {
    private String plntCd;
    private String pitmCd;
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
    private String pitmMstAprIdx;

    private String rjtUid;
    private String rjtDs;
    private String rjtRea;
    private String pitmTyp;
    private String pitmNm;
    private String pitmEn;
    private String sapPrdha;
    private String ansDurDay;
    private String crgDptCd;
    private String spcmNo;
    private String micYn;
    private String clltMtd;
    private String clltMcn;
    private String clltPla;
    private String smpStrgMtd;
    private String smpVolUnit;
    private String smpVolAns;
    private String smpVolEtc;
    private String smpVolStrg;

    private String mtrTyp;
    private String mtrMrp;
    private String labNo;
    private String brdAbbr;
    private String brdLne;
    private String nomMtr;
    private String etrCtnQty;
    private String rmtrSpec;
    private String nbr;
    private String ftnYn;
    private String pcs01;
    private String pcs02;
    private String pcs03;
    private String pcs04;
    private String useTrm;
    private String otcPrd;
    private String dmsEptYn;
    private String pearDiv;
    private String mkrVol;
    private String mkrVolUnit;
    private String busCrg;
    private String etnAnsReq;
    private String ctrptNo;

    private String specProcNm;
    private String rvsRea;
    private String crgDptNm;

    private String specUseVerYn;
    private String pitmSpecIdx;
    private String aitmSpecIdx;
    private String pitmTypNm;
    private String revwUnm;
    private String rjtUnm;

    private String qpSpecProcCd;
    private String qpSpecProcNm;
}
