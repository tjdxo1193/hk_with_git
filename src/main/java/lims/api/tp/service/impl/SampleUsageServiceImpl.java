package lims.api.tp.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.tp.dao.SampleUsageDao;
import lims.api.tp.enums.ApproveRequestDivision;
import lims.api.tp.enums.SampleUsageProgress;
import lims.api.tp.service.SampleUsageService;
import lims.api.tp.vo.SampleUsageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleUsageServiceImpl implements SampleUsageService {
    private final SampleUsageDao dao;
    private final ApproveService approveService;

    @Override
    public List<SampleUsageVO> find(SampleUsageVO param) {
        return dao.find(param);
    }

    @Override
    public List<SampleUsageVO> findSample(SampleUsageVO param) {
        return dao.findSample(param);
    }

    @Override
    public void requestApproveUsage(SampleUsageVO param) {
        int result = 0;
        param.setAprReqDiv(ApproveRequestDivision.USE_SAMPLE.getCode());
        ApproveVO approveInfo = setApproveInfo(param);
        approveService.create(approveInfo);
        param.setSmpUseProc(SampleUsageProgress.REQUEST_USE.getCode());
        param.setSmpUseAprIdx(approveInfo.getAprIdx());
        result += dao.requestApprove(param);
        if (result < 1) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void create(SampleUsageVO param) {
        int result = 0;
        param.setSmpUseProc(SampleUsageProgress.TEMP_SAVE.getCode());
        result += dao.create(param);
        if (result < 1) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void update(SampleUsageVO param) {
        int result = 0;
        result += dao.update(param);
        if (result < 1) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void delete(SampleUsageVO param) {
        int result = 0;
        result += dao.delete(param);
        if (result < 1) {
            throw new NoDeletedDataException();
        }
    }

    @Override
    public void requestCancelUsage(SampleUsageVO param) {
        int result = 0;
        param.setAprReqDiv(ApproveRequestDivision.USE_SAMPLE.getCode());
        ApproveVO approveInfo = setApproveInfo(param);
        param.setSmpUseProc(SampleUsageProgress.REQUEST_CANCEL_USE.getCode());
        approveService.create(approveInfo);
        param.setSmpUseAprIdx(approveInfo.getAprIdx());
        result += dao.requestApprove(param);
        if (result < 1) {
            throw new NoUpdatedDataException();
        }
    }

    private ApproveVO setApproveInfo(SampleUsageVO param) {
        ApproveVO approveInfo = new ApproveVO();
        approveInfo.setPlntCd(param.getPlntCd());
        approveInfo.setAprReqDiv(param.getAprReqDiv());
        approveInfo.setAprUid(param.getAprUid());
        approveInfo.setAprReqUid(param.getAprReqUid());
        return approveInfo;
    }
}
