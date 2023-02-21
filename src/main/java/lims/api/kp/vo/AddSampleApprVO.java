package lims.api.kp.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AddSampleApprVO {
    /* QS 추가 검체 관리 */
    private String plntCd;              // 사업장 코드
    private Integer addSmpIdx;          // 추가 검체 IDX
    private Integer ansIdx;             // 시험 IDX
    private Integer smpMngIdx;          // 검체 관리 IDX
    private String addSmpProc;          // S032 추가 검체 프로세스
    private String pitmTyp;             // S018 품목 유형
    private String pitmCd;              // 품목 코드
    private String pitmNm;              // 품목 명
    private String ansTyp;              // S023 시험 유형
    private String lotNo;               // 제조 번호
    private String batchNo;             // 배치 번호
    private String inpUnit;             // 입력 단위
    private String smpVolUnit;          // U005 검체량 단위
    private Integer reqSmpVol;          // 요청 검체량
    private String smpReqRea;           // U025 검체 요청 사유
    private String smpReqReaDtl;        // 검체 요청 사유 상세
    private String labelCd;             // 라벨 코드
    private Character delYn;            // 삭제 여부
    private String delRea;              // 삭제 사유
    private Integer addSmpAprReqIdx;    // 추가 검체 승인 요청 IDX
    private String smpReqDt;            // 검체 요청 일자
    private String rjtUid;              // 반려 UID
    private String rjtDs;               // 반려 일시
    private String rjtRea;              // 반려 사유
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;

    /* QS 검체 관리 */
    private String smpDpsProc;      // S027 검체 폐기 프로세스
    private String smpDivCd;        // S026 검체 구분 코드
    private Integer mngSmpVol;      // 관리 검체량
    private String smpStrgMtd;      // U014 검체 보관 방법
    private String makDt;           // 제조 일자
    private String strgLmt;         // 보관 기한
    private String useLmt;          // 사용 기한
    private Character irgYn;        // 이상 여부
    private String smpRmk;          // 검체 비고
    private String dpsExpDt;        // 폐기 예정 일자
    private String dpsFixDt;        // 폐기 확정 일자
    private Integer smpDpsAprIdx;   // 검체 폐기 승인 IDX
    private String dpsRea;          // 폐기 사유
    private String dpsCanRea;       // 폐기 취소 사유
    private Character dpsYn;        // 폐기 여부
    private String smpEtrDt;        // 검체 입고 일자

    /* 공통 승인 정보 */
    private String aprReqDiv;       // S005 승인 요청 구분
    private String aprReqUid;       // 승인 요청 UID
    private String aprReqDs;        // 승인 요청 일시
    private String aprReqIp;        // 승인 요청 IP
    private String aprReqRea;       // 승인 요청 사유
    private String aprUid;          // 승인자 UID
    private String aprDs;           // 승인 일시
    private String aprIp;           // 승인 IP
    private String aprRea;          // 승인 사유
    private String aprYn;           // 승인 여부

    /* 별칭 */
    private String ansNo;           // 시험 번호
    private String pitmTypNm;       // 품목 구분 명칭
    private String ansTypNm;        // 시험 유형 명칭
    private String smpVolUnitNm;    // 검체량 단위 명청
    private String smpReqReaNm;     // 검체 요청 사유 명칭
    private String aprReqNm;        // 요청자 성명
    private List<String> smpReqDtList = new ArrayList<>();
}
