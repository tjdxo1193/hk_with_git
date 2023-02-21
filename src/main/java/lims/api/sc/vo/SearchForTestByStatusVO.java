package lims.api.sc.vo;

import lims.api.common.domain.FileKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchForTestByStatusVO {
    /* QT_PITM_ANS_PROC (품목 시험 프로세스)*/
    private String plntCd;          // 사업장 코드
    private Integer ansIdx;         // 시험 IDX
    private Integer reqIdx;         // 의뢰 IDX
    private String pitmSpecIdx;     // 품목 규격서 IDX
    private String ansProcCd;       // S013 진행상태 코드
    private String sytJdg;          // S011 종합 판정
    private String reqDt;           // 의뢰 일자
    private String ansEdt;          // 시험 예정 일자
    private String ansNo;           // 시험 번호
    private String rcpDt;           // 접수 일자
    private String rcpDptCd;        // 접수 부서 코드
    private String rcpUid;          // 접수 UID
    private String rcpRmk;          // 접수 비고
    private String clltDt;          // 채취 일자
    private String clltUid;         // 채취 UID
    private String clltRmk;         // 채취 비고
    private String assNo;           // 지시 번호
    private String assDt;           // 지시 일자
    private String assSpcc;         // 지시 특이사항
    private Integer assAprReqIdx;   // 지시 승인 요청 IDX
    private String cplRqmDt;        // 완료 요구 일자
    private String wrkSttCd;        // U009 완료 상태 코드
    private String revwDt;          // 검토 일자
    private String revwUid;         // 검토 UID
    private String revwCmmt;        // 검토 의견
    private Integer rstAprReqIdx;   // 결과 승인 요청 IDX
    private String rjtUid;          // 반려 UID
    private String rjtDs;           // 반려 일시
    private String rjtRea;          // 반려 사유
    private String aprRea;          // 승인 사유
    private String hldYn;           // 보류 여부
    private String hldDs;           // 보류 일시
    private String hldUid;          // 보류 UID
    private String hldRea;          // 보류 사유
    private String hldCanlDs;       // 보류 취소 일시
    private String hldCanlUid;      // 보류 취소 UID
    private String hldCanlRea;      // 보류 취소 사유
    private String ansCanlDs;       // 시험 취소 일시
    private String ansCanlUid;      // 시험 취소 UID
    private String ansCanlRea;      // 시험 취소 사유
    private String oosYn;           // OOS 여부
    private String oosReqUid;       // OOS 의뢰 UID
    private String oosReqDs;        // OOS 의뢰 일시
    private String oosCd;           // OOS 코드

    /* QT_PITM_ANS_INFO (품목 시험 정보) */
    private String crgDptCd;        // 담당 부서 코드
    private String spcmNo;          // 표준 견본 번호
    private Integer ansFileIdx;     // 시험 첨부 파일 IDX
    private String clltMtd;         // U011 채취 방법
    private String clltMcn;         // U012 채취 기구
    private String clltPla;         // U013 채취 장소
    private String smpStrgMtd;      // U014 검체 보관 방법
    private String smpVolUnit;      // U005 검체량 단위
    private String smpVolAns;       // 검체량 시험
    private String smpVolEtc;       // 검체량 기타
    private String smpVolAdd;       // 검체량 추가
    private String smpVolMng;       // 검체량 관리폼
    private String ansNewYn;        // 최초 시험 여부
    private String micYn;           // 미생물 유무
    private String rtstYn;          // 재시험 여부
    private Integer ansOrgReqIdx;   // 원시험 의뢰 IDX
    private String emgYn;           // 긴급 여부
    private Integer arptRptIdx;     // 통합 성적서 리포트 IDX
    private String pkgMtrSpec;      // 포장재 규격
    private String rmtrSpec;        // 원료 규격
    private String rmtrCfmul;       // 원료 조성 성분비
    private String rptSpcc;         // 성적서 특이사항
    private String arptSpcc;        // 통합 성적서 특이사항

    /* QM_PITM_SPEC */
    private String pitmCd;              // 품목 코드
    private Integer pitmVer;            // 품목 버전
    private Integer aitmSpecIdx;        // 시험 항목 규격 IDX
    private String specProcCd;          // S008 규격서 진행 상태 코드
    private Character delYn;            // 삭제 여부
    private Character useVerYn;         // 사용 버전 여부
    private String rvsNo;               // 개정 번호
    private String docNo;               // 문서 번호
    private String rvsDt;               // 개정 일자
    private String enfoDt;              // 시행 일자
    private String rvsReaCd;            // U007 개정 사유 코드
    private String rvsCtt;              // 개정 내역
    private Character rvsDivPs;         // 개정 구분 PS
    private String revwDs;              // 검토 일시
    private String revwIp;              // 검토 IP
    private Integer pitmAnsSpecAprIdx;  // 품목 시험 규격 승인 IDX

    /* QM_PITM (품목) */
    private String pitmMstAprIdx;   // 품목 마스터 승인 IDX

    /* QM_PITM_INFO (품목 정보) */
    private String pitmTyp;         // S018 품목 유형
    private String pitmNm;          // 품목 명 (국문)
    private String pitmEn;          // 품목 명 (영문)
    private String sapPrdha;        // SAP 제품 계층 구조
    private Integer ansDurDay;      // 시험 소요 일수

    /* QT_PITM_ANS_REQ (품목 시험 의뢰) */
    private String ispReqNo;        // 검사 요청 번호
    private String ispReqDt;        // 검사 요청 일자
    private String mtrCd;           // 자재 코드
    private String mtrNm;           // 자재 명
    private String batchNo;         // 배치 번호
    private String etrNo;           // 입고 수량
    private String inpUnit;         // 입력 단위
    private String savePla;         // 저장 위치
    private String etrDt;           // 입고 일자
    private String phsOrderTyp;     // 구매 오더 유형
    private String phsOrderNo;      // 구매 오더 번호
    private String phsOrderItm;     // 구매 오더 항목
    private String itmCtg;          // 아이템 카테고리
    private String phsUnitPre;      // 구매 단가
    private String amtUnit;         // 가격 단위
    private String currCd;          // 통화키
    private String amtLoccurr;      // 금액 현지 통화
    private String phsOrderQty;     // 구매 오더 수량
    private String phsOrderUnit;    // 구매 오더 단위
    private String splCd;           // 공급사 코드
    private String splNm;           // 공급사 명
    private String phsCrgNm;        // 구매 담당자
    private String dlvYn;           // 택배 유무
    private String vdrCtrtDt;       // 납품 약정 일자
    private String vdrRsvTm;        // 납품 예약 시간
    private String vdrRptRcpCrst;   // 거래처 성적서 접수 현황
    private String lotNo;           // 제조 번호
    private String repBomNo;        // 대체 BOM 번호
    private String splLotNo;        // 공급사 제조 번호
    private String makDt;           // 제조 일자
    private String strgLmt;         // 보관 기한
    private String useLmt;          // 사용 기한
    private String pdtOrderTyp;     // 생산 오더 유형
    private String pdtOrderNo;      // 생산 오더 번호
    private String itnPrsCompCd;    // 내부 임가공 업체 코드
    private String otmPrsCompNm;    // 내부 임가공 업체 명
    private String makEqp;          // 제조 설비
    private String wrkNm;           // 작업자 명
    private String mtrDocNo;        // 자재 문서 번호
    private String mtrDocYr;        // 자재 문서 연도
    private String mtrDocItm;       // 자재 문서 항목
    private String csmBpCd;         // 고객처 BP 코드
    private String csmNm;           // 고객처 명
    private String addCol1;         // 입고 유형 (A: 구매, B: 생산, C: 수동 의뢰)
    private String addCol2;         // 입고 취소 여부 (취소: X)
    private String addCol3;         // 추가 필드 3
    private String addCol4;         // 추가 필드 4
    private String revDs;           // 수신 일지

    /* MS_AITM (MS 시험 항목) */
    private String aitmCd;          // 시험 항목 코드
    private String aitmKn;          // 시험 항목 국문
    private String aitmEn;          // 시험 항목 영문
    private String aitmAbbr;        // 시험 항목 약어
    private String vriaKn;
    private String vriaNo;

    /* 파일 */
    private List<MultipartFile> addedFiles = new ArrayList<>();
    private List<FileKey> removedFileIds = new ArrayList<>();

    /* 별칭 */
    private String pitmTypNm;
    private String clltNm;
    private String rjtNm;
    private String rjtDt;
    private String sytJdgNm;
    private String hldDt;
    private String hldNm;
    private Integer fileIdx;
    private String fileCnt;
    private String crgDptNm;
    private String testDiv;
    private List<String> reqDtList = new ArrayList<>();
    private List<String> rcpDtList = new ArrayList<>();
    private String ansDptNm;
    private String ansNm;
    private String specTypNm;
    private String jdgTypNm;
    private String rstVal;
    private String rstUnitNm;
    private String rstJdgNm;
    private String orderNo;
    private String slvJdgCfmNm;
    private String slvJdgNonCfmNm;
}
