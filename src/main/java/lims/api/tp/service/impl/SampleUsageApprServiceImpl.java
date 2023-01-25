package lims.api.tp.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.tp.dao.SampleUsageApprDao;
import lims.api.tp.enums.SampleUsageProgress;
import lims.api.tp.service.SampleUsageApprService;
import lims.api.tp.vo.SampleUsageApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleUsageApprServiceImpl implements SampleUsageApprService {
    private final SampleUsageApprDao dao;
    private final ApproveService approveService;

    @Override
    public List<SampleUsageApprVO> find(SampleUsageApprVO param) {
        param.setSmpUseProc(SampleUsageProgress.REQUEST_USE.getCode());
        return dao.find(param);
    }

    @Override
    public void approve(List<SampleUsageApprVO> params) {
        int result = 0;

        for (SampleUsageApprVO param : params) {
            param.setSmpUseProc(SampleUsageProgress.APPROVE_USE.getCode());
            approveService.approve(param.getSmpUseAprIdx());
            result += dao.approve(param);
        }

        if (result != params.size()) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void reject(List<SampleUsageApprVO> params) {
        int result = 0;

        for (SampleUsageApprVO param : params) {
            param.setSmpUseProc(SampleUsageProgress.REJECT_USE.getCode());
            result += dao.reject(param);
        }

        if (result != params.size()) {
            throw new NoUpdatedDataException();
        }
    }
}
