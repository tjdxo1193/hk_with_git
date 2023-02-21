package lims.api.kp.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AddSampleSearchVO {
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
    private String labelCd;            // 라벨 코드
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

    /* 별칭 */
    private String ansNo;
    private String addSmpProcNm;
    private String pitmTypNm;
    private String ansTypNm;
    private String smpVolUnitNm;
    private String smpReqReaNm;
    private List<String> smpReqDtList = new ArrayList<>();
}
