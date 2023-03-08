package lims.api.in.vo;

import lims.api.common.domain.FileKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InstManageVO {
    /* EM_EQM_INFO(EM 기기 정보) */
    private String plntCd;      // 사업장 코드
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
    private String istDt;       // 설치 일자
    private String dpsDt;       // 폐기 일자
    private String qualAprDt;       // Qual 승인 일자
    private String calAprDt;        // Cal 승인 일자
    private String rglChkAprDt;     // 정기 점검 승인 일자
    private String useRng;      // 사용 가능 범위
    private String qttRng;      // 정량 범위
    private String rmk;         // 비고
    private Integer eqmFileIdx;     // 기기 파일 IDX
    private String sapAstNo;        // SAP 자산 번호
    private String sapAstNm;        // SAP 자산 명
    private String sapCrtDt;        // SAP 생성 일자
    private String sapChgDt;        // SAP 변경 일자
    private String sapAcqDt;        // SAP 취득 일자
    private String sapSaleDpsDt;        // SAP 매각_폐기 일자
    private String sapAddDesc;      // SAP 추가 설명
    private String sapCrgNmEmid;        // SAP 담당자 사번
    private String sapCosc;         // SAP 코스트 센터
    private Integer sapOrco;        // SAP 취득가액
    private Integer sapAccd;        // SAP 감가상각 누계액
    private Character pmsChkTagtYn;     // PMS 점검 대상 여부
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;

    /* EM_EQM_ACSR(EM 기기 구성품)*/
    private Integer acsrSeq;        // 구성품 순번
    private String acsrNm;      // 구성품 명
    private String acsrModNm;       // 구성품 모델 명
    private String acsrSrlNo;       // 구성품 Serial No.

    /* 파일 관련 */
    private List<MultipartFile> addedFiles = new ArrayList<>();
    private List<FileKey> removedFileIds = new ArrayList<>();

    /* Alias */
    private List<InstManageVO> addedAcsrList = new ArrayList<>();    // 추가된 구성품 정보
    private List<InstManageVO> editedAcsrList = new ArrayList<>();    // 수정된 구성품 정보
    private List<InstManageVO> removedAcsrList = new ArrayList<>();    // 삭제된 구성품 정보
    private List<String> dpsDtList = new ArrayList<>();
    private List<String> qualAprDtList = new ArrayList<>();
    private List<String> calAprDtList = new ArrayList<>();
    private List<String> rglChkAprDtList = new ArrayList<>();
    private String eqmDivNm;
    private String eqmCrstNm;
    private String eqmSttNm;
    private String istPlaNm;
    private String crgNm;
    private String chkCylNm;
    private String quaCylNm;
    private String calCylNm;
    private String anlkl;
    private String bzdat;
}