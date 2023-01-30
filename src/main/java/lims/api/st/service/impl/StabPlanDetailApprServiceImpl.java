package lims.api.st.service.impl;

import lims.api.common.service.ApproveService;
import lims.api.st.dao.StabPlanDetailApprDao;
import lims.api.st.enums.SbtAnsProcess;
import lims.api.st.service.StabPlanDetailApprService;
import lims.api.st.vo.StabPlanDetailApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StabPlanDetailApprServiceImpl implements StabPlanDetailApprService {

    private final StabPlanDetailApprDao stabPlanDetailApprDao;
    private final ApproveService approveService;

    @Override
    public List<StabPlanDetailApprVO> findAll(StabPlanDetailApprVO param) {
        List<String> sbtAnsProcList = new ArrayList<>();
        sbtAnsProcList.add(SbtAnsProcess.APPROVE_REQUEST.getSbtAnsProc());
        sbtAnsProcList.add(SbtAnsProcess.STOP_REQUEST.getSbtAnsProc());
        sbtAnsProcList.add(SbtAnsProcess.STOP_CANCEL_REQUEST.getSbtAnsProc());
        param.setSbtAnsProcList(sbtAnsProcList);
        return stabPlanDetailApprDao.findAll(param);
    }

    @Override
    public int approve(List<StabPlanDetailApprVO> param) {
        param.forEach(item -> {
            String preSbtAnsProc = item.getSbtAnsProc();
            SbtAnsProcess sbtAnsProcess = SbtAnsProcess.getApproveCode(preSbtAnsProc);
            String sbtAnsProc = sbtAnsProcess.getSbtAnsProc();
            item.setSbtAnsProc(sbtAnsProc);

            if(sbtAnsProcess.equals(SbtAnsProcess.APPROVED)) {
                approveService.approve(item.getSbtAnsPlnAprIdx());
            }
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
            String preSbtAnsProc = item.getSbtAnsProc();
            SbtAnsProcess sbtAnsProcess = SbtAnsProcess.getRejectCode(preSbtAnsProc);
            String sbtAnsProc = sbtAnsProcess.getSbtAnsProc();
            item.setSbtAnsProc(sbtAnsProc);
        });

        param.forEach(this::reject);
        return 1;
    }

    @Override
    public int reject(StabPlanDetailApprVO param) {
        return stabPlanDetailApprDao.reject(param);
    }

}
