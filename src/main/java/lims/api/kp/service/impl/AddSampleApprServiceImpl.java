package lims.api.kp.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.kp.dao.AddSampleApprDao;
import lims.api.kp.enums.AddSampleProgress;
import lims.api.kp.service.AddSampleApprService;
import lims.api.kp.vo.AddSampleApprVO;
import lims.api.tp.enums.SampleDisposalProgress;
import lims.api.tp.enums.SampleDivision;
import lims.api.tp.enums.SampleUsageProgress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddSampleApprServiceImpl implements AddSampleApprService {
    private final AddSampleApprDao dao;
    private final ApproveService approveService;

    @Override
    public List<AddSampleApprVO> find(AddSampleApprVO param) {
        param.setAddSmpProc(AddSampleProgress.REQUEST_ADD_SAMPLE.getCode());
        return dao.find(param);
    }

    @Override
    public void approve(AddSampleApprVO param) {
        int result = 0;
        param.setAddSmpProc(AddSampleProgress.APPROVE_ADD_SAMPLE.getCode());
        result += dao.approve(param);
        createSample(param);
        approveService.approve(param.getAddSmpAprReqIdx());
        if (result < 1) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void reject(AddSampleApprVO param) {
        int result = 0;
        param.setAddSmpProc(AddSampleProgress.REJECT_ADD_SAMPLE.getCode());
        result += dao.reject(param);
        if (result < 1) {
            throw new NoUpdatedDataException();
        }
    }

    private void createSample(AddSampleApprVO param) {
        int result = 0;
        param.setSmpDpsProc(SampleDisposalProgress.SAMPLE_STORED.getCode());
        param.setSmpDivCd(SampleDivision.ADDED_SAMPLE.getCode());
        result += dao.createSample(param);
        if (result < 1) {
            throw new NoCreatedDataException();
        }
    }
}
