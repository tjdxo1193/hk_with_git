package lims.api.st.service.impl;

import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.ms.enums.CycleDivision;
import lims.api.ms.enums.SpecProgress;
import lims.api.st.dao.StabPlanDao;
import lims.api.st.enums.*;
import lims.api.st.service.StabPlanService;
import lims.api.st.vo.StabPlanVO;
import lims.api.ts.enums.TestProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StabPlanServiceImpl implements StabPlanService {
    private final StabPlanDao stabPlanDao;
    private final ApproveService approveService;

    // 첫 시험
    static final String ACC_MARK_NM = "Initial";

    @Override
    public List<StabPlanVO> findAll(StabPlanVO param) {
        List<StabPlanVO> result = stabPlanDao.findAll(param);
        return result;
    }

    @Override
    public List<StabPlanVO> findAllItem(StabPlanVO param) {
        param.setQpsSpecProcCd(SpecProgress.APPROVED.getCode());
        param.setQpapAnsProcCd(TestProcess.TEST_FINISH.getProcessCode());
        return stabPlanDao.findAllItem(param);
    }

    @Override
    public Map<String, Object> getDetail(StabPlanVO param) {
        Map<String, Object> result = new HashMap<>();

        List<StabPlanVO> headerDetail = new ArrayList<>();
        List<Map<String, Object>> aitmData = new ArrayList<>();
        List<StabPlanVO> decisionData = new ArrayList<>();
        // 데이터 조회
        try {
            headerDetail = stabPlanDao.getHeaderDetail(param);
        } catch(Exception e) {
            e.printStackTrace();
        }

        // 시험항목 조회
        try {
//            List<String> ansDtList = stabPlanDao.getAnsDt(param);
            List<StabPlanVO> stSbtAnsList = stabPlanDao.getSbtAnsIdx(param);
            List<String> ansDtList = stSbtAnsList.stream().map(StabPlanVO::getAnsDt).collect(Collectors.toList());
            List<Integer> sbtAnsIdxList = stSbtAnsList.stream().map(StabPlanVO::getSbtAnsIdx).collect(Collectors.toList());

            if(stSbtAnsList.size() > 0) {
                param.setSbtAnsIdxList(sbtAnsIdxList);
                List<Map<String, Object>> pivotDataList = stabPlanDao.getAitmData(param);

                if(pivotDataList != null && pivotDataList.size() > 0) {
                    for(Map<String, Object> pivotData : pivotDataList) {
                        for(int i = 0; i < sbtAnsIdxList.size(); i++) {
                            String sbtAnsIdxKey = "'" + sbtAnsIdxList.get(i) + "'";
                            String aitmSeqCount = pivotData.get(sbtAnsIdxKey).toString();
                            Boolean aitmSeqExit = !aitmSeqCount.equals("0");
                            pivotData.put(("col" + i), aitmSeqExit);
                            pivotData.remove(sbtAnsIdxKey);
                        }

                        aitmData.add(pivotData);
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        result.put("headerDetail", headerDetail);
        result.put("aitmData", aitmData);

        return result;
    }

    @Override
    public int getSbtAnsForValidate(StabPlanVO param) {
        return stabPlanDao.getSbtAnsForValidate(param);
    }

    @Override
    @Transactional
    public int save(StabPlanVO param) {
        int result = 0;

        param.setDelYn("N");
        param.setSbtAnsProc(SbtAnsProcess.SAVE.getSbtAnsProc());
        param.setSbtPlnStt(SbtPlnSttProcess.SAVE.getProcessCode());

        ApproveVO approveVO = this.setApproveVO(param);
        approveService.requestApprove(approveVO);

        param.setSbtAnsPlnAprIdx(approveVO.getAprIdx());

        // 안정성 시험 계획 번호 부여
        String sbtAnsPlnNo = this.getSbtAnsPlnNo(param);
        param.setSbtAnsPlnNo(sbtAnsPlnNo);

        // 안정성 시험 보고서 번호 부여(문서번호도 통일)
        String sbtAnsRptNo = this.getSbtAnsRptNo(param);
        param.setSbtAnsRptNo(sbtAnsRptNo);
        param.setDocNo(sbtAnsRptNo);

        result += stabPlanDao.save(param);

        // 안정성 시험 생성
        result += this.saveSideEffects(param);

        return result;
    }

    /**
     * @Title 안정성 시험 계획 번호 부여
     */
    public String getSbtAnsPlnNo(StabPlanVO param) {
        String sbtAnsPlnNo = "S";

        // 이번 년도 두자리 구하기
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String currentYear = formatter.format(now);
        String lastTwoOfCurrentYear = currentYear.substring(currentYear.length() - 2, currentYear.length());

        // 시험종류 코드 구하기
        String ansKnd = param.getAnsKnd();
        AnsKndCode ansKndCode = AnsKndCode.valueOfCode(ansKnd);

        // 구분자
        String delimiter = "-";

        // 계획서, 보고서 구분
        String pOrR = "P";

        // 순번 구하기
        param.setNumReg("000");
        String num = stabPlanDao.findTop1ByOrderBySbtPlnIdxDesc(param);

        // 결과
        sbtAnsPlnNo += (lastTwoOfCurrentYear + ansKndCode + delimiter + pOrR + num);
        sbtAnsPlnNo = sbtAnsPlnNo.replaceAll(" ", "");

        return sbtAnsPlnNo;
    }

    /**
     * @Title 안정성 시험 보고서 번호 부여
     */
    public String getSbtAnsRptNo(StabPlanVO param) {
        String sbtAnsRptNo = "";

        // 시험종류 코드 구하기
        String ansKnd = param.getAnsKnd();
        AnsKndCode ansKndCode = AnsKndCode.valueOfCode(ansKnd);

        // 구분자
        String delimiter = "-";

        // 이번 년도 두자리 구하기
        Date now = new Date();
        SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
        String currentYear = yearFormatter.format(now);
        String lastTwoOfCurrentYear = currentYear.substring(currentYear.length() - 2, currentYear.length());

        // 이번 달 두자리 구하기
        SimpleDateFormat monthFormatter = new SimpleDateFormat("MM");
        String currentMonth = monthFormatter.format(now);

        // 순번 구하기
        param.setNumReg("0000");
        String num = stabPlanDao.findTop1ByOrderBySbtPlnIdxDesc(param);

        // 결과
        sbtAnsRptNo += (ansKndCode + delimiter + lastTwoOfCurrentYear + currentMonth + num);
        sbtAnsRptNo = sbtAnsRptNo.replaceAll(" ", "");

        return sbtAnsRptNo;
    }

    /**
     * @Title 안정성계획 생성 시, 안정성 시험 추가.
     * @Note 이 로직은 SY_ANS_TRM테이블에 표기된 주기에 따라 ST_SBT_ANS 테이블에 시험 데이터를 추가해주는 로직이다.
     *      이후, ST_SBT_ANS에 등록된 데이터는 쿼츠, 스케줄러 등으로 시험 날짜를 조회할 것이다.(추후)
     *          ㄴ 조회된 데이터가 있다면 QT_PITM_ANS_REQ, QT_PITM_ANS_PROC에 데이터가 추가될 것이다.
     */
    @Override
    public int saveSideEffects(StabPlanVO param) {
        //
        int result = 0;

        StabPlanVO mappedAnsTrm = stabPlanDao.findOneSyAnsTrm(param);
        if(mappedAnsTrm == null) {
            return result;
        }

        String ansStrDt = param.getAnsStrDt();
        Integer ansItv = mappedAnsTrm.getAnsItv();
        Integer ansTrm = mappedAnsTrm.getAnsTrm();
        Integer initialSbtAnsIdx = 0;

        if(ansStrDt != null && ansItv != null && ansTrm != null) {
            int count = (ansTrm / ansItv);
            String sbtAnsStt = SbtAnsSttProcess.TEST_ON_PROCESS.getProcessCode();
            StabPlanVO initialStSbtAns = this.getNewAns(param, mappedAnsTrm, 0, ACC_MARK_NM, sbtAnsStt);
            stabPlanDao.saveAns(initialStSbtAns);

            // Initial 데이터에서 사용할 sbtAnsIdx 값
            initialSbtAnsIdx = initialStSbtAns.getSbtAnsIdx();

            // Initial(시작날짜, 첫 시험만 안정성시험중, 그 이후는 시험시작전 으로)
            sbtAnsStt = SbtAnsSttProcess.TEST_BEFORE.getProcessCode();
            String accMarkNm;
            for(int i = 1; i <= count; i++) {
                accMarkNm = String.valueOf((i * ansItv));

                StabPlanVO stSbtAns = this.getNewAns(param, mappedAnsTrm, i, accMarkNm, sbtAnsStt);
                result += stabPlanDao.saveAns(stSbtAns);
            }

            // Initial 데이터는 모두 기본으로 ST_SBT_ANS_AITM에 넣어준다
            List<StabPlanVO> amitmList = stabPlanDao.getAmitm(param);
            for(StabPlanVO amitmData : amitmList) {
                StabPlanVO stSbtAnsAitm = this.getNewAnsAitm(param, amitmData, initialSbtAnsIdx);
                result += stabPlanDao.saveAitm(stSbtAnsAitm);
            }
        }

        return result;
    }

    /**
     * @Title 객체에 담을 값을 가져가서 생성 후 반환
     * @return StabPlanVO stSbtAns
     */
    public StabPlanVO getNewAns(StabPlanVO param, StabPlanVO mappedAnsTrm, int current, String accMarkNm, String sbtAnsStt) {
        StabPlanVO stSbtAns = new StabPlanVO();

        String ansStrDt = param.getAnsStrDt();
        String ansCylDiv = mappedAnsTrm.getAnsCylDiv();
        Integer ansItv = mappedAnsTrm.getAnsItv();
        Integer ansTrm = mappedAnsTrm.getAnsTrm();
        String ansDt = this.getAnsDt(param, ansItv, current, ansCylDiv);

        stSbtAns.setPlntCd(param.getPlntCd());
        stSbtAns.setSbtPlnIdx(param.getSbtPlnIdx());
        stSbtAns.setSbtAnsStt(sbtAnsStt);
        stSbtAns.setAnsDelYn("N");
        stSbtAns.setAccMarkNm(accMarkNm);
        stSbtAns.setAnsDt(ansDt);

        return stSbtAns;
    }

    /**
     * @Title 객체에 담을 값을 가져가서 생성 후 반환
     * @return StabPlanVO stSbtAnsAitm
     */
    public StabPlanVO getNewAnsAitm(StabPlanVO param, StabPlanVO amitmData, Integer initialSbtAnsIdx) {
        StabPlanVO stSbtAnsAitm = new StabPlanVO();

        String plntCd = param.getPlntCd();
        Integer sbtAnsIdx = initialSbtAnsIdx;
        String amitmCd = amitmData.getAmitmCd();

        stSbtAnsAitm.setPlntCd(plntCd);
        stSbtAnsAitm.setSbtAnsIdx(sbtAnsIdx);
        stSbtAnsAitm.setAmitmCd(amitmCd);

        return stSbtAnsAitm;
    }

    /**
     *  @Title 시험 날짜 구하기
     *  @note SimpleDateFormat, Date 이용, SY_ANS_TRM에 등록된 시험 기간 데이터에 따라
     *        날짜를 계산하여 반환함.
    */
    public String getAnsDt(StabPlanVO param, int ansItv, int current, String ansCylDiv) {
        // 휴일 날짜 계산용 클라스
        String ansStrDt = param.getAnsStrDt();
        String ansDt = null;

        try {
            final String dateFormat = "yyyy-MM-dd";
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

            Date date = formatter.parse(ansStrDt);
            Calendar calendar = Calendar.getInstance();

            final int calDiv = (CycleDivision.Day.getCode().equals(ansCylDiv)) ? Calendar.DATE : (CycleDivision.Month.getCode().equals(ansCylDiv)) ? Calendar.MONTH : 999;

            calendar.setTime(date);
            calendar.add(calDiv, (ansItv * current));
            date = calendar.getTime();

            ansDt = formatter.format(date);
        } catch(ParseException e) {
            throw new RuntimeException(e);
        }

        return ansDt;
    }

    @Override
    public int update(StabPlanVO param) {
        param.setSbtAnsProc(SbtAnsProcess.SAVE.getSbtAnsProc());
        return stabPlanDao.update(param);
    }

    @Override
    @Transactional
    public int delete(StabPlanVO param) {
        int result = 0;
        // 안정성계획 삭제
        result += stabPlanDao.delete(param);
        // 안정성시험 삭제
        List<StabPlanVO> ansListForDelete = stabPlanDao.getAnsForDelete(param);
        for(StabPlanVO itemForDeleteAns : ansListForDelete) {
            result += stabPlanDao.deleteAns(itemForDeleteAns);
        }

        // 안정성시험 항목 삭제
        List<StabPlanVO> aitmListForDelete = stabPlanDao.getAitmForDelete(param);
        for(StabPlanVO itemForDeleteAitm : aitmListForDelete) {
            result += stabPlanDao.deleteAitm(itemForDeleteAitm);
        }

        return result;
    }

    @Override
    public int stopRequest(StabPlanVO param) {
        param.setSbtAnsProc(SbtAnsProcess.STOP_REQUEST.getSbtAnsProc());
        param.setSbtPlnStt(SbtPlnSttProcess.PLN_APPROVE_REQUEST.getProcessCode());
        ApproveVO approveVO = this.setApproveVO(param);
        approveVO.setAprIdx(param.getSbtAnsPlnAprIdx());
        approveVO.setAprReqRea(param.getAprReqRea());
        approveService.requestApprove(approveVO);
        return stabPlanDao.stopRequest(param);
    }

    @Override
    public int stopCancelRequest(StabPlanVO param) {
        param.setSbtAnsProc(SbtAnsProcess.STOP_CANCEL_REQUEST.getSbtAnsProc());
        param.setSbtPlnStt(SbtPlnSttProcess.PLN_APPROVE_REQUEST.getProcessCode());
        ApproveVO approveVO = this.setApproveVO(param);
        approveVO.setAprIdx(param.getSbtAnsPlnAprIdx());
        // SY_APR_INFO의 APR_REQ_DIV(승인 요청 구분, S005)을 바인딩은 하지만, 쿼리 상, 해당 값을 수정하지 않는다.
        approveService.requestApprove(approveVO);
        return stabPlanDao.stopCancelRequest(param);
    }

    @Override
    @Transactional
    public int approveRequest(StabPlanVO param) {
        param.setSbtAnsProc(SbtAnsProcess.APPROVE_REQUEST.getSbtAnsProc());
        param.setSbtPlnStt(SbtPlnSttProcess.PLN_APPROVE_REQUEST.getProcessCode());
        ApproveVO approveVO = this.setApproveVO(param);
        approveVO.setAprIdx(param.getSbtAnsPlnAprIdx());
        // SY_APR_INFO의 APR_REQ_DIV(승인 요청 구분, S005)을 바인딩은 하지만, 쿼리 상, 해당 값을 수정하지 않는다.
        approveService.requestApprove(approveVO);

        return stabPlanDao.approveRequest(param);
    }

    @Override
    public int saveAns(StabPlanVO param) {
        int result = 0;
        if(param.getSbtAnsIdx() == null) {
            param.setSbtAnsStt(SbtAnsSttProcess.TEST_BEFORE.getProcessCode());
            param.setAnsDelYn("N");

            result = stabPlanDao.saveAns(param);
        } else {
            stabPlanDao.updateAns(param);
        }

        return result;
    }

    @Override
    public int deleteAns(StabPlanVO param) {
        return stabPlanDao.deleteAns(param);
    }

    @Override
    @Transactional
    public int saveDetailPlanReg(StabPlanVO param) {
        int result = 0;
        List<StabPlanVO> detailPlanRegList = param.getDetailPlanRegList();

        // 기존에 저장된 데이터 중 Initial 데이터 제외하고 삭제(초기화)
        result += stabPlanDao.deleteAitmSideEffect(param);

        // 체크박스 체크된 데이터 저장
        for(StabPlanVO stabPlanVO : detailPlanRegList) {
            result += stabPlanDao.saveAitm(stabPlanVO);
        }

        return result;
    }

    public ApproveVO setApproveVO(StabPlanVO param) {
        ApproveVO approveInfo = new ApproveVO();
        approveInfo.setPlntCd(param.getPlntCd());
        approveInfo.setAprReqUid(param.getLoginUserUid());
//        approveInfo.setAprReqDiv(param.getSbtAnsProc());

        approveInfo.setAprReqDiv(ApproveRequestStDivType.STAB_PLAN_VERSION.getCode());
        if(param.getAprUid() != null) {
            approveInfo.setAprUid(param.getAprUid());
        }

        return approveInfo;
    }
}
