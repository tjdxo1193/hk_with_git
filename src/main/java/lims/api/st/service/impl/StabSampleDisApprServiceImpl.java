package lims.api.st.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.st.dao.StabSampleDisApprDao;
import lims.api.st.service.StabSampleDisApprService;
import lims.api.st.vo.StabSampleDisApprVO;
import lims.api.tp.enums.SampleDisposalProgress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StabSampleDisApprServiceImpl implements StabSampleDisApprService {
    private final StabSampleDisApprDao dao;
    private final ApproveService approveService;

    @Override
    public List<StabSampleDisApprVO> find(StabSampleDisApprVO param) {
        return dao.find(param);
    }

    @Override
    public void approve(List<StabSampleDisApprVO> params) {
        int result = 0;

        for (StabSampleDisApprVO param : params) {
            if (param.getSmpDpsProc().equals(SampleDisposalProgress.REQUEST_DISPOSAL.getCode())) {
                param.setSmpDpsProc(SampleDisposalProgress.APPROVE_DISPOSAL.getCode());
            } else {
                param.setSmpDpsProc(SampleDisposalProgress.APPROVE_DISPOSAL_CANCEL.getCode());
            }
            approveService.approve(param.getSmpDpsAprIdx());
            result += dao.approve(param);
        }

        if (result != params.size()) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void reject(List<StabSampleDisApprVO> params) {
        int result = 0;

        for (StabSampleDisApprVO param : params) {
            if (param.getSmpDpsProc().equals(SampleDisposalProgress.REQUEST_DISPOSAL.getCode())) {
                param.setSmpDpsProc(SampleDisposalProgress.REJECT_DISPOSAL.getCode());
            } else {
                param.setSmpDpsProc(SampleDisposalProgress.REJECT_DISPOSAL_CANCEL.getCode());
            }
            result += dao.reject(param);
        }
        if (result != params.size()) {
            throw new NoUpdatedDataException();
        }
    }
}