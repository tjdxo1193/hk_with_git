package lims.api.ts.vo;

import lims.api.common.domain.FileKey;
import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TestRequestVO implements UpdateDetect {
    private String plntCd; //사업장 코드
    /*의뢰정보*/
    private int reqIdx; // 의뢰 IDX
    private String ispReqNo; //검사 요청 번호
    private String ispReqDt; //검사 요청 일자
    private String mtrCd; //자재 코드
    private String mtrNm; //자재 명
    private String batchNo; //배치 번호
    private String etrQty; //입고 수량
    private String inpUnit; //입력 단위
    private String savePla; //저장 위치
    private String etrDt; //입고 일자
    private String phsOrderTyp; //구매 오더 유형
    private String phsOrderNo; //구매 오더 번호
    private String phsOrderItm; //구매 오더 항목
    private String itmCtg; //아이템 카테고리
    private String phsUnitPre; //구매 단가
    private String amtUnit; //가격 단위
    private String currCd; //통화키
    private String amtLoccurr; //금액 현지통화
    private String phsOrderQty; //구매 오더 수량
    private String phsOrderUnit; //구매 오더 단위
    private String splCd; //공급사 코드
    private String splNm; //공급사 명
    private String phsCrgNm; //구매 담당자
    private String dlvYn; //택배 유무
    private String vdrCtrtDt; //납품 약정 일자
    private String vdrRsvTm; //납품 예약 시간
    private String vdrRptRcpCrst; //거래처 성적서 접수 현황
    private String lotNo; //제조번호
    private String repBomNo; //대체 BOM 번호
    private String splLotNo; //공급사 제조번호
    private String makDt; //제조 일자
    private String strgLmt; //보관 기한
    private String useLmt; //사용 기한
    private String pdtOrderTyp; //생산 오더 유형
    private String pdtOrderNo; //생산 오더 번호
    private String itnPrsCompCd; //내부 임가공 업체 코드
    private String itnPrsCompNm; //내부 임가공 업체 명
    private String makEqp; //제조 설비
    private String wrkNm; //작업자명
    private String mtrDocNo; //자재 문서 번호
    private String mtrDocYr; //자재 문서 연도
    private String mtrDocItm; //자재 문서 항목
    private String csmBpCd; //고객처 BP 코드
    private String csmNm; //고객처 명
    private String udtDs; //수정 일시
    private String revDs; //수신 일시
    /*품목정보*/
    private String pitmCd; // 품목 코드
    private String pitmNm; // 품목 명 국문
    private String pitmEn; // 품목 명 영문
    private String pitmTyp; // 품목 유형
    private String pitmTypNm;
    private String pitmVer; // 품목버전
    private String useVerYn; // 사용 버전 여부
    /*SAP품목정보*/
    private String labNo; //LAB NO
    private String brdAbbr; // 브랜드 약어
    private String etrCtnQty; // 입고 용기 수량
    private String rmtrSpec; // 원료 규격
    private String ftnYn; // 기능성 유무
    private String pcs01; // 공정01
    private String pcs02; // 공정02
    private String pcs03; // 공정03
    private String pcs04; // 공정04
    private String useTrm; // 사용 기간
    private String otcPrd; // OTC 제품
    private String dmsEptYn; // 내수 수출 여부
    private String pearDiv; // 펄 구분
    private String mkrVol; // 표시 용량
    private String mkrVolUnit; // 표시 용량 단위
    private String etnAnsReq; // 외부 시험 의뢰
    private String ctrptNo; // CT성적서 번호
    private String perNo; // 허가 번호
    private String chagVol; // 실충전 용량
    private String chagVolUnit; // 실충전 용량 단위
    private String dioYn; // 디옥산관리대상
    private String prbInYn; // 파라벤포함여부
    private String prbFeYn; // 파라벤프리관리대상
    private String pnxFeYn; // 페녹시에탄올프리관리대상

    /*시험정보*/
    private int ansIdx; //시험 IDX
    private String ansNo; //시험번호
    private int assNo; //지시번호
    private String ansTypNm;
    private String ansTyp;
    private String sytJdg;
    private String sytJdgNm;
    private String ansProcCd;
    private String ansProcNm;
    private List<String> TestProcessList;
    private String reqDt;
    private String reqRmk;
    private String reqUid;
    private String reqDptCd;
    private List<String> searchReqDt;
    private String rcpDt; //접수일자
    private String rcpUnm; //접수자
    private String assDt;
    private String assUnm;
    private String revwDt;
    private String revwUnm;
    private String rstAprDs;
    private String rstAprUnm;
    private String assSpcc;
    private String revwCmmt;
    private String pitmSpcc;
    private String nonCfmRea;

    /* 시험항목 */
    private int pitmSpecIdx; //품목 규격서 IDX
    private int rstSeq; //시험항목 순번
    private String ispDurTm; //검사 소요 시간
    private String eqmUseTm; //기기 사용 시간
    private String amitmCd; //시험항목별방법 코드
    private String vriaKn; //VARIANT 국문
    private String vriaNo; //VARIANT NO
    private String aitmKn; //시험항목 국문

    private String perspecTxt; //허가규격 텍스트
    private String owcSpecTxt; //자사 규격 텍스트
    private String specTxtEn; //규격 텍스트 영문
    private String perSlvLow; //허가 기준 하한
    private String perSlvUpp; //허가 기준 상한
    private String perSlvDesc; //허가 기준 서술
    private String owcSlvLow; //자사 기준 하한
    private String owcSlvUpp; //자사 기준 상한
    private String owcSlvDesc; //자사 기준 서술
    private String rstDpnt; //결과 소수점
    private String rptPrtSlvVal; //성적서 출력 기준 값
    private String rptPrtItmNm; //성적서 출력 항목 명
    private String rptPrtYn; //성적서 출력 여부
    private String specTypNm; //규격 유형
    private String jdgTypNm; //판정 유형
    private String slvJdgCfmNm; //기준 적합 판정
    private String slvJdgNonCfmNm; //기준 부적합 판정
    private String rstUnitNm; //결과 단위 코드
    private String ansDptNm; //시험부서명

    private String mkrQty; //표시량
    private String aitmRmk; //시험항목 비고

    private List<String> formReqDt; //의뢰일자
}
