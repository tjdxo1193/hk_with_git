package lims.api.tp.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.tp.dao.SampleDisApprDao;
import lims.api.tp.enums.ApproveRequestDivision;
import lims.api.tp.enums.SampleDisposalProgress;
import lims.api.tp.service.SampleDisApprService;
import lims.api.tp.vo.SampleDisApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleDisApprServiceImpl implements SampleDisApprService {
    private final SampleDisApprDao dao;
    private final ApproveService approveService;

    @Override
    public List<SampleDisApprVO> find(SampleDisApprVO param) {
        param.setAprReqDiv(ApproveRequestDivision.DISPOSE_SAMPLE.getCode());
        return dao.find(param);
    }

    @Override
    public void approve(List<SampleDisApprVO> params) {
        int result = 0;

        for (SampleDisApprVO param : params) {
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
    public void reject(List<SampleDisApprVO> params) {
        int result = 0;

        for (SampleDisApprVO param : params) {
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
