package lims.api.st.service.impl;

import lims.api.common.service.ApproveService;
import lims.api.st.dao.StabPlanDetailApprDao;
import lims.api.st.enums.SbtAnsProcess;
import lims.api.st.service.StabPlanDetailApprService;
import lims.api.st.vo.StabPlanDetailApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StabPlanDetailApprServiceImpl implements StabPlanDetailApprService {

    private final StabPlanDetailApprDao stabPlanDetailApprDao;
    private final ApproveService approveService;

    @Override
    public List<StabPlanDetailApprVO> findAll(StabPlanDetailApprVO param) {
        param.setSbtAnsProc(SbtAnsProcess.APPROVE_REQUEST.getProcessCode());
        return stabPlanDetailApprDao.findAll(param);
    }

    @Override
    public Map<String, List<StabPlanDetailApprVO>> getDetail(StabPlanDetailApprVO param) {
        Map<String, List<StabPlanDetailApprVO>> result = new HashMap<>();

        List<StabPlanDetailApprVO> headerDetail = new ArrayList<>();
        List<StabPlanDetailApprVO> aitmData = new ArrayList<>();
        List<StabPlanDetailApprVO> decisionData = new ArrayList<>();
        // 데이터 조회
        try {
            headerDetail = stabPlanDetailApprDao.getHeaderDetail(param);
        } catch(Exception e) {
            e.printStackTrace();
        }

        // 시험항목 조회
        try {
//            aitmData = stabPlanDao.getAnsList(param);
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Decision 데이터 조회(실제로 사용은..)
        try {
//            decisionData = stabPlanDao.getDecisionData(param);
        } catch(Exception e) {
            e.printStackTrace();
        }


        result.put("headerDetail", headerDetail);
        result.put("aitmData", aitmData);
        result.put("decisionData", decisionData);

        return result;
    }

    @Override
    public int approve(List<StabPlanDetailApprVO> param) {
        param.forEach(item -> {
            item.setSbtAnsProc(SbtAnsProcess.APPROVED.getProcessCode());
            approveService.approve(item.getSbtAnsPlnAprIdx());
        });

        param.forEach(this::approve);
        return 1;
    }

    @Override
    public int approve(StabPlanDetailApprVO param) {
        return stabPlanDetailApprDao.approve(param);
    }

    @Override
    public int reject(List<StabPlanDetailApprVO> param) {
        param.forEach(item -> {
            item.setSbtAnsProc(SbtAnsProcess.APPROVE_REJECT.getProcessCode());
        });

        param.forEach(this::reject);
        return 1;
    }

    @Override
    public int reject(StabPlanDetailApprVO param) {
        return stabPlanDetailApprDao.reject(param);
    }

}
