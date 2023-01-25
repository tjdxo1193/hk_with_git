package lims.api.mt.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorTestRequestVO implements UpdateDetect {
    private String plntCd; //사업장 코드
    private Integer mitmReqIdx; //모니터링항목 의뢰 IDX
    private Integer mitmSpecIdx; //모니터링항목 규격서 IDX
    private String ansProcCd;
    private String ansProcNm; //시험 진행상태
    private String reqNo; //의뢰 번호
    private String reqDt; //의뢰 일자
    private String ansEdt; //시험 예정일자
    private String reqDs; //의뢰 일시
    private String reqUid;
    private String reqDptCd;
    private String reqDptNm; //의뢰 부서
    private String reqRmk;
    private String reqCanlUid;
    private String reqCanlRea;
    private String mitmCd; //모니터링항목
    private String mitmNm; //모니터링항목명
    private String mitmWrkPlcDivNm; //모니터링항목 작업실 구분
    private String mitmPitmDivNm; //모니터링항목 품목 구분
    private String point;
    private String roomno;
    private String grade;
    private String crgDptNm; //담당 부서
    private String ansCylRuleNm; //시험주기
}
