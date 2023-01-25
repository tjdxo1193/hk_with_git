package lims.api.ms.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemApprRequestVO {
    /*QM_PITM*/
    private String plntCd;
    private String pitmCd;
    private Integer pitmVer;
    private String specProcCd;
    private Character delYn;
    private Character useVerYn;
    private String rvsNo;
    private String docNo;
    private String rvsDt;
    private String enfoDt;
    private String rvsReaCd;
    private String rvsCtt;
    private Character rvsDivPs;
    private String revwUid;
    private String revwDs;
    private String revwIp;
    private Integer pitmMstAprIdx;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;
    /*QM_PITM_INFO*/
    private String pitmTyp;
    private String pitmNm;
    private String pitmEn;
    private String sapPrdha;
    private Integer ansDurDay;
    private String crgDptCd;
    private String spcmNo;
    private Character micYn;
    private String clltMtd;
    private String clltMcn;
    private String clltPla;
    private String smpStrgMtd;
    private String smpVolUnit;
    private Integer smpVolAns;
    private Integer smpVolEtc;
    private Integer smpVolStrg;
    private String pkgMtrSpec;
    /*QM_PITM_INFO_SAP*/
    private String mtrTyp;
    private String mtrMrp;
    private String labNo;
    private String brdAbbr;
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
    /* ApproveVO */
    private String aprReqDiv;       // S005 승인 요청 구분
    private String aprReqUid;       // 승인 요청 UID
    private String aprReqDs;        // 승인 요청 일시
    private String aprReqIp;        // 승인 요청 IP
    private String aprReqRea;       // 승인 요청 사유
    private String aprUid;      // 승인자 UID
    private String aprDs;       // 승인 일시
    private String aprIp;       // 승인 IP
    private String aprRea;      // 승인 사유
    private String aprYn;       // 승인 여부

    /* Alias*/
    private String specProcNm;
    private String pitmTypNm;
    private String crgDptNm;
    private String clltMtdNm;
    private String clltMcnNm;
    private String clltPlaNm;
}
