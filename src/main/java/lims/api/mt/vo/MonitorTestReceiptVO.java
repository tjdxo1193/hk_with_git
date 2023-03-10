package lims.api.mt.vo;

import lims.api.common.domain.UpdateDetect;
import lims.api.common.vo.ApproveVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MonitorTestReceiptVO implements UpdateDetect {
    private int mitmReqIdx; //모니터링항목 의뢰 IDX
    private int mitmSpecIdx; //모니터링항목 규격서 IDX
    private int aitmSpecIdx; //시험항목 규격 IDX
    private String ansProcCd; //시험 진행상태
    private String ansProcNm;
    private String reqNo; //의뢰 번호
    private String reqDt; //의뢰 일자
    private String ansEdt; //시험 예정일자
    private String reqDs; //의뢰 일시
    private String reqDptNm; //의뢰 부서
    private String reqRmk;
    private String rcpDt; //접수일자
    private String rcpDs; //접수일시
    private String rcpRmk;
    private String ansNo; //시험번호
    private String mitmCd; //모니터링항목
    private String mitmWrkStudioDiv; //모니터링항목 작업동
    private String mitmWrkStudioDivNm;
    private String upperMitmWrkPlcDivNm; //모니터링항목 작업소 구분명
    private String upperMitmWrkPlcDiv; //모니터링항목 작업소 구분
    private String mitmWrkPlcDivNm; //모니터링항목 작업실 구분명
    private String mitmWrkPlcDiv; //모니터링항목 작업실 구분
    private String upperMitmPitmDiv; //품목 구분
    private String upperMitmPitmDivNm; //품목 구분명
    private String mitmPitmDivNm; //모니터링항목 품목 구분
    private String mitmPitmDiv;
    private String point;
    private String roomno;
    private String grade;
    private String crgDptNm; //담당 부서
    private String crgDptCd; //담당 부서
    private String ansCylCd; //시험주기코드
    private String ansCylRuleNm; //시험주기
    private String wrkDivNm; //작업구분
    private String rcpDptCd; //접수부서
    private String rcpUid; //접수자

    private int aitmSeq; //시험항목 순번
    private String plntCd; //사업장 코드
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

    private List<String> searchReqDt; //의뢰일

    /*승인정보*/
    private ApproveVO approveInfo;
    private int assAprReqIdx; //지시승인 idx
}
