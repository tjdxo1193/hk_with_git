package lims.api.ms.vo;

import lims.api.common.domain.Paging;
import lims.api.common.domain.UpdateDetect;
import lims.api.ms.domain.MsElnCtRptFileKey;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ItemManageVO extends Paging implements UpdateDetect {
    /* QM_PITM(QM 품목) */
    private String plntCd;      // 사업장 코드
    private String pitmCd;      // 품목 코드
    private Integer pitmVer;        // 품목 버전
    private String specProcCd;      // S008 규격서 진행 상태 코드
    private String qpSpecProcCd;      // S008 규격서 진행 상태 코드

    private Character delYn;        // 삭제 여부
    private Character useVerYn;        // 사용 버전 여부
    private String rvsNo;       // 개정 번호
    private String docNo;       // 문서 번호
    private String rvsDt;       // 개정 일자
    private String enfoDt;      // 시행 일자
    private String rvsReaCd;        // U007 개정 사유 코드
    private String rvsCtt;      // 개정 내역
    private String rvsDivPs;        // 개정 구분 PS
    private String revwUid;     // 검토 UID
    private String revwDs;      // 검토 일시
    private String revwIp;      // 검토 IP
    private Integer pitmMstAprIdx;       // 품목 마스터 승인 IDX
    private String rjtUid;      // 반려 UID
    private String rjtDs;       // 반려 일시
    private String rjtRea;      // 반려 사유

    /* QM_PITM_INFO(QM 품목 정보)*/
    private String pitmTyp;        // S018 품목 유형
    private String pitmNm;      // 품목 명
    private String pitmEn;      // 품목 영문
    private String sapPrdha;        // SAP 제품 계층 구조
    private Integer ansDurDay;      // 시험 소요 일수
    private String crgDptCd;        // 담당 부서 코드
    private String spcmNo;       // 표준 견본 번호
    private String micYn;        // 미생물 유무
    private String clltMtd;     // U011 채취 방법
    private String clltMcn;     // U012 채취 기구
    private String clltPla;     // U013 채취 장소
    private String smpStrgMtd;      // U014 검체 보관 방법
    private String smpVolUnit;      // U005 검체량 단위
    private Integer smpVolAns;      // 검체량 시험
    private Integer smpVolEtc;      // 검체량 기타
    private Integer smpVolStrg;     // 검체량 보관
    private String pkgMtrSpec; // 포장재 규격

    /* QM_PITM_INFO_SAP */
    private String mtrTyp;      // 자재 유형
    private String mtrMrp;      // 자제 MRP
    private String labNo;       // LAB 번호
    private String brdAbbr;     // 브랜드 약어
    private String nomMtr;      // 기본 자재
    private String etrCtnQty;       // 입고 용기 수량
    private String rmtrSpec;        // 원료 규격
    private String nbr;             // 호수
    private Character ftnYn;        // 기능성 유무
    private String pcs01;       // 공정 01
    private String pcs02;       // 공정 02
    private String pcs03;       // 공정 03
    private String pcs04;       // 공정 04
    private String useTrm;      // 사용 기간
    private String otcPrd;      // OTC 제품
    private Character dmsEptYn;     // 내수 수출 여부 
    private String pearDiv;     // 펄 구분
    private String mkrVol;      // 표시 용량
    private String mkrVolUnit;      // 표시 용량 단위
    private String etnAnsReq;       // 외부 시험 의뢰
    private String ctrptNo;     // CT 성적서 번호
    private String perNo;       // 허가 번호
    private String chagVol;     // 실충전 용량
    private String chagVolUnit;     // 실 충전 용량 단위
    private String dioYn;       // 디옥산 관리 대상
    private String prbInYn;     // 파라벤 포함 여부
    private String prbFeYn;     // 파라벤 프리 관리 대상
    private String pnxFeYn;     // 페녹시에탄올 프리 관리 대상
    private String rmtrCfmul;  // 원료 조성성분비
    private String nomUnit;  // 기본단위

    /* QM_PITM_SPEC */
    private Integer pitmSpecIdx;
    private Integer aitmSpecIdx;

    /* QM_PITM_SPEC_AITM */
    private Integer aitmSeq;
    private String aitmRmk;
    private String mkrQty;
    /* QM_PITM_AITM_SPEC*/
    private Integer aitmSpecVer;

    /* QM_PKGA */
    private String pkgaCd;
    private Integer pkgaVer;

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

    /* Alias */
    private String plntNm;      // 사업장 명칭
    private String specProcNm;      // 규격서 진행 상태
    private String pitmTypNm;       // 품목구분 명칭
    private String crgDptNm;        // 담당 부서 명칭
    private String smpStrgMtdNm;        // 검체 보관 방법 명칭
    private String clltMtdNm;        // 검체 채취 방법 명칭
    private String rvsReaNm;        // 개정 사유 명칭
    private String clltMcnNm;       // 검체 보관 방법 명칭
    private String clltPlaNm;       // 검체 보관 장소 명칭
    private Integer pitmSpecCount;      // 생성된 규격서의 개수
    private String revwNm;      // 검토자 성명
    private String rjtNm;       // 반려자 성명

    private Integer newAitmSpecIdx;

    private String udtNm;
    private String udtDs;

    //  MS_ELN_CT_RPT_FILE
    private String ctId;                        // CT 성적서 ID
    private String ctSeq;                       // 차수
    private String fileName;                    // 파일명
    private byte[] data;                        // 파일 데이터
    private String crtDs;                       // 수신일시
    private String crtDt;                       // 수신일자
//    private String udtDs;                     // 수정일시
    private String udtDt;                       // 수정일자

    private String name;                        // 파일명(DropZone용 임의 변수)
    private String type;                        // 파일타입(DropZone용 임의 변수)
    private Long size;                          // 파일크기(DropZone용 임의 변수)
    private byte[] src;                         // 파일(DropZone용 임의 변수)
    private Integer fileIdx;                    // 파일 idx(DropZone용 임의 변수)
    private Integer fileSrlno;                    // 파일 seq(DropZone용 임의 변수)
//    private Integer fileSeq;                    // 파일 seq(DropZone용 임의 변수)
//    private Integer sortOrdr;                   // 정렬 순서

    List<MultipartFile> AddedFiles;             // 추가된 파일 리스트
    List<MsElnCtRptFileKey> RemovedFileIds;   // 삭제된 파일 리스트

    // MS_SAP_BOM
    private String posnr;                           // 품목
    private String stlal;                           // 대체 BOM
    private String werks;                           // 플랜트
    private String matnr;                           // 자재 번호
    private String maktx;                           // 자재내역
    private String mtart;                           // 자재유형
    private String name1;                           // 플랜트내역1
    private String dispo;                           // MRP 관리자
    private String dsnam;                           // MRP 관리자 내역
    private String stktx;                           // 대체 BOM 내역
    private String bmeng;                           // 기준수량
    private String bmein;                           // 단위
    private String validFrom;                       // 효력시작일
    private String validTo;                         // 효력종료일
    private String stlnr;                           // BOM No
    private String ztext;                           // BOM 내역
    private String lkenz;                           // BOM 삭제지시자
    private String postp;                           // 품목 범주
    private String ptext;                           // 품목 범주 텍스트
    private String idnrk;                           // 구성 품목
    private String idnrkMakt;                       // 구성 품목 내역
    private String potx1;                           // 구성 품목 텍스트
    private String menge;                           // 구성 품목 수량
    private String meins;                           // 구성 품목 단위
    private String fmnge;                           // 고정 수량
    private String beikz;                           // 자재 공급 지시자
//    private String crtDs;                           // 수신일시
//    private String udtDs;                           // 수정일시
}