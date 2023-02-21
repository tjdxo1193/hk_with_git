package lims.api.lb.vo;

import lims.api.common.vo.ApproveVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabEventSearchVO {
    // QE_LAB_EVT
    private String plntCd;              // 사업장 코드
    private Integer labEvtIdx;          // 실험실 이벤트 IDX
    private String ansNo;               // 시험 번호
    private Integer ansIdx;             // 시험 IDX
    private Integer rstSeq;             // 결과 순번
    private String pitmTyp;             // ZS018 품목 유형
    private String pitmTypNm;           // 품목 유형 명(임의)
    private String pitmCd;              // 품목 코드
    private String pitmNm;              // 품목 명
    private String orderNo;             // 오더 번호
    private String lotNo;               // 제조번호
    private String batchNo;             // 배치 번호
    private String amitmCd;             // 시험항목별방법 코드
    private String amitmNm;             // 시험항목별방법 명
    private String ocrDt;               // 발생 일자
    private String ansUid;              // 시험 UID
    private String ansUidNm;            // 시험자(임의)
    private String crtvMsr;             // 상세의견및조치사항
    private String tstLogRpubYn;        // 시험일지 재발행 여부
    private String revwCmmt;            // 검토 의견
    private String revwUid;             // 검토 UID
    private String revwUidNm;           // 검토자(임의)
    private String revwDs;              // 검토 일시
    private String revwDt;              // 검토 일자
    private String revwIp;              // 검토 IP
    private Integer labEvtAprIdx;       // 실험실 이벤트 승인 IDX
    private String rjtUid;              // 반려 UID
    private String rjtUidNm;              // 반려자(임의)
    private String rjtDs;               // 반려 일시
    private String rjtDt;               // 반려 일자(임의 추가)
    private String rjtRea;              // 반려 사유

    // ETC
    private String loginUserUid;        // 로그인 유저 UID
    private String loginUserIp;         // 로그인 유저 IP
    private ApproveVO approveInfo;      // 승인 관련 Object
    private String aprReqDiv;           // S005, 승인 요청 구분
    private String aprYn;               // 승인 여부
    private String aprUid;               // 승인 UID
}
