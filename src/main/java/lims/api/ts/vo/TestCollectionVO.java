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
public class TestCollectionVO implements UpdateDetect {
    private String plntCd; //사업장 코드
    /*의뢰정보*/
    private int reqIdx; // 의뢰 IDX
    private String ansProcCd;
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
    private String pitmTypNm; // 품목 유형
    private String pitmVer; // 품목버전
    private String useVerYn; // 사용 버전 여부
    /*시험정보*/
    private String ansIdx; //시험 IDX
    private String ansNo; //시험번호
    private String ansTyp;
    private String ansTypNm;
    private String rcpDt; //접수일자
    private String rcpUnm; //접수자
    private String rcpRmk;
    private String clltDt; //채취일자
    private String clltUid; //채취자
    private String clltUnm;
    private String clltRmk; //채취비고
    /*검체 정보*/
    private String spcmNo; //표준견본 번호
    private String ansFileIdx; //시험 첨부파일 IDX
    private String clltMtd; //채취 방법
    private String clltMcn; //채취 기구
    private String clltPla; //채취 장소
    private String smpStrgMtd; //검체 보관 방법
    private String smpVolUnit; //검체량 단위
    private int smpVolAns; //검체량 시험
    private int smpVolEtc; //검체량 기타
    private int smpVolStrg; //검체량 보관
    private String micYn; //미생물 유무
    private int smpVolTot; //총검체량

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

    /*승인정보*/
    private ApproveVO approveInfo;
    private int assAprReqIdx; //지시승인 idx

    private List<MultipartFile> addedFiles = new ArrayList<>();
    private List<FileKey> removedFileIds = new ArrayList<>();
    private Integer fileIdx; // file Idx
    private String fileCnt;

    private List<String> formReqDt; //의뢰일자

    /*검체관리 정보*/
    private int smpMngIdx;
    private String smpDivCd;
    private int mngSmpVol;
}
