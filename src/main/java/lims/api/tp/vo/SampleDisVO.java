package lims.api.tp.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SampleDisVO {
    /* QS 검체 관리 */
    private String plntCd;
    private Integer smpMngIdx;
    private String smpDpsProc;
    private String pitmTyp;
    private String pitmCd;
    private String pitmNm;
    private String ansTyp;
    private Integer ansIdx;
    private String smpDivCd;
    private Integer mngSmpVol;
    private String smpStrgMtd;
    private String smpVolUnit;
    private String lotNo;
    private String batchNo;
    private String inpUnit;
    private String makDt;
    private String strgLmt;
    private String useLmt;
    private Character irgYn;
    private String smpRmk;
    private String dpsExpDt;
    private String dpsFixDt;
    private Integer smpDpsAprIdx;
    private String dpsRea;
    private String dpsCanlRea;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;
    private Character delYn;
    private Character dpsYn;
    private String smpEtrDt;

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
    private String smpDpsNm;        // 검체 폐기 프로세스 명칭
    private String pitmTypNm;       // 품목 분류 명칭
    private String ansTypNm;        // 시험 분류 명칭
    private String smpDivNm;        // 검체 분류 명칭
    private String smpStrgMtdNm;    // 검체 보관 방법 명칭
    private String smpVolUnitNm;    // 검체량 단위 명칭
    private String ansProcNm;       // 시험 진행 상태 명칭
    private String ansProcCd;       // S013 시험 진행 상태 코드
    private Integer reqIdx;         // 의뢰 IDX
    private String rcpDt;           // 시험 접수 날짜
    private List<String> dpsExpDtList = new ArrayList<>();
    private List<String> smpEtrDtList = new ArrayList<>();
}
