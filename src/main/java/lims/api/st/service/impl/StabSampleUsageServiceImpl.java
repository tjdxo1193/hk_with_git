package lims.api.st.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.st.dao.StabSampleUsageDao;
import lims.api.st.service.StabSampleUsageService;
import lims.api.st.vo.StabSampleUsageVO;
import lims.api.tp.enums.ApproveRequestDivision;
import lims.api.tp.enums.SampleUsageProgress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StabSampleUsageServiceImpl implements StabSampleUsageService {
    private final StabSampleUsageDao dao;
    private final ApproveService approveService;

    @Override
    public List<StabSampleUsageVO> find(StabSampleUsageVO param) {
        return dao.find(param);
    }

    @Override
    public void requestApproveUse(StabSampleUsageVO param) {
        int result = 0;
        param.setAprReqDiv(ApproveRequestDivision.USE_SAMPLE.getCode());
        ApproveVO approveInfo = setApproveInfo(param);
        approveService.create(approveInfo);
        param.setSmpUseAprIdx(approveInfo.getAprIdx());
        result += dao.requestApproveUse(param);
        if (result < 1) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void create(StabSampleUsageVO param) {
        int result = 0;
        param.setSmpUseProc(SampleUsageProgress.TEMP_SAVE.getCode());
        result += dao.create(param);
        if (result < 1) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void update(StabSampleUsageVO param) {
        int result = 0;
        result += dao.update(param);
        if (result < 1) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void delete(StabSampleUsageVO param) {
        int result = 0;
        result += dao.delete(param);
        if (result < 1) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void requestCancelUse(StabSampleUsageVO param) {
        int result = 0;
        param.setAprReqDiv(ApproveRequestDivision.USE_SAMPLE.getCode());
        ApproveVO approveInfo = setApproveInfo(param);
        approveService.create(approveInfo);
        param.setSmpUseAprIdx(approveInfo.getAprIdx());
        result += dao.requestCancelUse(param);

        if (result < 1) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public List<StabSampleUsageVO> findSample(StabSampleUsageVO param) {
        return dao.findSample(param);
    }

    private ApproveVO setApproveInfo(StabSampleUsageVO param) {
        ApproveVO approveInfo = new ApproveVO();
        approveInfo.setPlntCd(param.getPlntCd());
        approveInfo.setAprReqDiv(param.getAprReqDiv());
        approveInfo.setAprUid(param.getAprUid());
        approveInfo.setAprReqUid(param.getAprReqUid());
        approveInfo.setAprReqIp(param.getUdtIp());
        return approveInfo;
    }
}
