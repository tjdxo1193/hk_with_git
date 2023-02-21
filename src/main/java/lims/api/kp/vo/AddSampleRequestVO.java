package lims.api.kp.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AddSampleRequestVO {
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

    /* 공통 승인 정보 */
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

    /* 별칭 */
    private String ansNo;           // 시험 번호
    private String smpVolUnitNm;    // 요청 샘플 단위 명칭
    private String smpReqReaNm;     // 요청 사유 명칭
    private String addSmpProcNm;    // 추가 검체 프로세스 명칭
    private String ansTypNm;        // 시험 유형 명칭
    private String pitmTypNm;
    private String rjtNm;
    private List<String> smpReqDtList = new ArrayList<>();
}
