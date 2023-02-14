package lims.api.ms.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorManageVO implements UpdateDetect {
    private String plntCd;      // 사업장 코드
    private String mitmCd;        // 모니터링항목 코드
    private String mitmWrkPlcDiv;     // 모니터링 항목 작업실 구분
    private String mitmPitmDiv;       // 모니터링 항목 품목 구분
    private String point;           // 포인트
    private String roomno;          //
    private String grade;           //
    private String wrkDiv;
    private String ansStrDt;        // 시험 시작 일자
    private String ansCylCd;        // 시험 주기 코드
    private String crgDptCd;        // 담당 부서 코드
    private String perSpec;         // 허가 규격
    private String revwDurTm;       // 검토 소요 시간
    private String aprDurTm;        // 승인 소요 시간
    private Character useYn;        // 사용 여부
    private Character delYn;        // 삭제 여부

    /* Alias */
    private String mitmWrkStudioDiv;        // 작업동 코드
    private String mitmWrkPlcUpperDiv;      // 작업소 코드
    private String mitmPitmUpperDiv;      // 상위 품목 코드
    private String mitmPitmNm;        // 모니터링 항목 이름
    private String mitmWrkPlcNm;      // 모니터링 작업실 이름
    private String ansCylNm;        // 시험 주기 이름
    private String crgDptNm;        // 담당 부서 이름
    private String gradeNm;         // grade 구분 이름
    private String wrkDivNm;        // 작업 구분 이름
    private String upperMitmPitmCd;
    private String upperMitmPitmNm;

}
