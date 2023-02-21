package lims.api.mt.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MonitorTestInstructionVO implements UpdateDetect {
    private int mitmReqIdx; //모니터링항목 의뢰 IDX
    private int mitmSpecIdx; //모니터링항목 규격서 IDX
    private int aitmSpecIdx; //시험항목 규격 IDX
    private int assAprReqIdx;
    private String plntCd; //사업장 코드
    private String ansProcCd; //시험 진행상태
    private String ansProcNm;
    private String ansNo; //시험번호
    private String reqNo; //의뢰 번호
    private String reqDt; //의뢰 일자
    private String ansEdt; //시험 예정일자
    private String reqDs; //의뢰 일시
    private String reqUnm; //의뢰자
    private String reqDptNm; //의뢰 부서
    private String rcpDt; //접수일자
    private String rcpDs; //접수일시
    private String rcpRmk;
    private String rcpUid; //접수자
    private String rcpDptCd; //접수부서
    private int assNo; //지시번호
    private String assDt; //지시일자
    private String assDs; //지시일시
    private String assSpcc;
    private String cplRqmDt; //완료요구일자
    private String mitmCd; //모니터링항목
    private String mitmWrkStudioDiv; //모니터링항목 작업동
    private String mitmWrkStudioDivNm;
    private String upperMitmWrkPlcDivNm; //모니터링항목 작업소 구분명
    private String upperMitmWrkPlcDiv; //모니터링항목 작업소 구분
    private String mitmWrkPlcDivNm; //모니터링항목 작업실 구분명
    private String mitmWrkPlcDiv; //모니터링항목 작업실 구분
    private String upperMitmPitmDiv; //품목 구분
    private String upperMitmPitmDivNm; //품목 구분명
    private String mitmPitmDiv; //모니터링항목 품목
    private String mitmPitmDivNm; //모니터링항목 품목명
    private String point;
    private String roomno;
    private String grade;
    private String crgDptCd; //담당 부서
    private String crgDptNm; //담당 부서
    private String ansCylCd; //시험주기코드
    private String ansCylRuleNm; //시험주기
    private String wrkDivNm; //작업구분

    private String userId;
    private List<String> searchRcpDt; //접수일

    private int rstSeq; //결과 순번
    private String rstProcNm; //결과진행상태
    private String rstProcCd; //결과진행상태코드
    private String ispDurTm; //검사 소요 시간
    private String eqmUseTm; //기기 사용 시간
    private String amitmCd; //시험항목별방법 코드
    private String vriaKn; //VARIANT 국문
    private String vriaNo; //VARIANT NO
    private String aitmKn; //시험항목 국문
    private String ansEqmCd; //분석 기기 코드
    private String smpClltQty; //검체채취수량
    private String smpClltUnit; //검체채취단위
    private String smpClltUnitNm;
    private String smpClltDt; //검체채취일자
    private String clltTmStr; //검체채취시작
    private String clltTmEnd; //검체채취종료

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
    private String specTyp; //규격 유형
    private String jdgTypNm; //판정 유형
    private String jdgTyp;
    private String slvJdgCfmNm; //기준 적합 판정
    private String slvJdgCfm;
    private String slvJdgNonCfmNm; //기준 부적합 판정
    private String slvJdgNonCfm;
    private String rstUnitNm; //결과 단위 코드
    private String rstUnit; //결과 단위 코드
    private String ansDptNm; //시험부서명

}
