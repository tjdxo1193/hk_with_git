package lims.api.st.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.st.dao.StabSampleUsageApprDao;
import lims.api.st.service.StabSampleUsageApprService;
import lims.api.st.vo.StabSampleUsageApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StabSampleUsageApprServiceImpl implements StabSampleUsageApprService {
    private final StabSampleUsageApprDao dao;
    private final ApproveService approveService;

    @Override
    public List<StabSampleUsageApprVO> find(StabSampleUsageApprVO param) {
        return dao.find(param);
    }

    @Override
    public void approve(StabSampleUsageApprVO param) {
        int result = 0;
        if (param.getSmpUseProc().equals("S0280200")) {
            param.setSmpUseProc("S0280300");
        } else if (param.getSmpUseProc().equals("S0280400")) {
            param.setSmpUseProc("S0280500");
        }

        approveService.approve(param.getSmpUseAprIdx());
        result += dao.approve(param);

        if (result < 1) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void reject(StabSampleUsageApprVO param) {
        int result = 0;
        if (param.getSmpUseProc().equals("S0280200")) {
            param.setSmpUseProc("S0280110");
        } else if (param.getSmpUseProc().equals("S0280400")) {
            param.setSmpUseProc("S0280310");
        }
        result += dao.reject(param);
        if (result < 1) {
            throw new NoUpdatedDataException();
        }
    }
}
