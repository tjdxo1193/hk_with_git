package lims.api.in.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstManageByItemVO {
    /* QM_PITM(QM 품목) */
    private String plntCd;              // 사업장 코드
    private String pitmCd;              // 품목 코드
    private Integer pitmVer;            // 품목 버전
    private String specProcCd;          // S008 규격서 진행 상태 코드
    private Character delyn;            // 삭제 여부
    private Character useVerYn;         // 사용 버전 여부
    private String rvsNo;               // 개정 번호
    private String docNo;               // 문서 번호
    private String rvsDt;               // 개정 일자
    private String enfoDt;              // 시행 일자
    private String rvsReaCd;            // U007 개정 사유 코드
    private String rvsCtt;              // 개정 내역
    private String rvsDivPs;            // 개정 구분 PS
    private String revwUid;             // 검토 UID
    private String revwDs;              // 검토 일시
    private String revwIp;              // 검토 IP
    private Integer pitmMstAprIdx;      // 품목 마스터 승인 IDX
    private String rjtUid;              // 반려 UID
    private String rjtDs;               // 반려 일시
    private String rjtRea;              // 반려 사유

    /* QM_PITM_INFO(QM 품목 정보)*/
    private String pitmTyp;        // S018 품목 유형
    private String pitmNm;      // 품목 명
    private String pitmEn;      // 품목 영문
    private String sapPrdha;        // SAP 제품 계층 구조
    private Integer ansDurDay;      // 시험 소요 일수
    private String crgDptCd;        // 담당 부서 코드
    private String spcmNo;       // 표준 견본 번호
    private Character micYn;        // 미생물 유무
    private String clltMtd;     // U011 채취 방법
    private String clltMcn;     // U012 채취 기구
    private String clltPla;     // U013 채취 장소
    private String smpStrgMtd;      // U014 검체 보관 방법
    private String smpVolUnit;      // U005 검체량 단위
    private Integer smpVolAns;      // 검체량 시험
    private Integer smpVolEtc;      // 검체량 기타
    private Integer smpVolMng;      // 검체량 관리폼
    private String pkgMtrSpec;      // 포장재 규격
    /* EM_EQM_INFO(EM 기기 정보) */
    private String eqmCd;       // 기기 코드
    private String eqmNm;       // 기기 이름
    private String eqmDiv;      // U015 기기 분류
    private String eqmCrst;     // U016 기기 현황
    private String eqmStt;      // U017 기기 상태
    private String istPla;      // U018 설치 장소
    private String makComp;     // 제조 회사
    private String splComp;     // 공급 회사
    private String modNm;       // 모델 이름
    private String srlNo;       // Serial No.
    private String crgUid;      // 담당자 UID
    private String iq;          // IQ
    private String oq;          // OQ
    private String pq;          // PQ
    private String aprNo;       // 승인 번호
    private String chkCyl;      // U019 점검 주기
    private String quaCyl;      // U019 Qualification 주기
    private String calCyl;      // U019 Calibration 주기
    private String etrDt;       // 입고 일자
    private String istDt;               // 설치 일자
    private String dpsDt;               // 폐기 일자
    private String qualAprDt;           // Qual 승인 일자
    private String calAprDt;            // Cal 승인 일자
    private String rglChkAprDt;         // 정기 점검 승인 일자
    private String useRng;              // 사용 가능 범위
    private String qttRng;              // 정량 범위
    private String rmk;                 // 비고
    private Integer eqmFileIdx;         // 기기 파일 IDX
    private String sapAstNo;            // SAP 자산 번호
    private String sapAstNm;            // SAP 자산 명
    private String sapCrtDt;            // SAP 생성 일자
    private String sapChgDt;            // SAP 변경 일자
    private String sapAcqDt;            // SAP 취득 일자
    private String sapSaleDpsDt;        // SAP 매각_폐기 일자
    private String sapAddDesc;          // SAP 추가 설명
    private String sapCrgNmEmid;        // SAP 담당자 사번
    private String sapCosc;             // SAP 코스트 센터
    private Integer sapOrco;            // SAP 취득가액
    private Integer sapAccd;            // SAP 감가상각 누계액
    private Character pmsChkTagtYn;     // PMS 점검 대상 여부
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;

    /* Alias */
    private String pitmTypNm;
    private String crgDptNm;
    private String specProcNm;
}
