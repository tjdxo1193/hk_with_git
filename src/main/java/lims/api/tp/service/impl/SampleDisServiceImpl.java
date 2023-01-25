package lims.api.tp.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.tp.dao.SampleDisDao;
import lims.api.tp.enums.ApproveRequestDivision;
import lims.api.tp.enums.SampleDisposalProgress;
import lims.api.tp.service.SampleDisService;
import lims.api.tp.vo.SampleDisVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleDisServiceImpl implements SampleDisService {
    private final SampleDisDao dao;
    private final ApproveService approveService;

    @Override
    public List<SampleDisVO> find(SampleDisVO param) {
        return dao.find(param);
    }

    @Override
    public void requestDisposal(List<SampleDisVO> params) {
        int result = 0;
        for (SampleDisVO param : params) {
            param.setAprReqDiv(ApproveRequestDivision.DISPOSE_SAMPLE.getCode());
            ApproveVO approveInfo = setApproveInfo(param);
            approveService.create(approveInfo);
            param.setSmpDpsProc(SampleDisposalProgress.REQUEST_DISPOSAL.getCode());
            param.setSmpDpsAprIdx(approveInfo.getAprIdx());
            result += dao.requestDis(param);
        }
        if (result != params.size()) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void requestDisposalCancel(List<SampleDisVO> params) {
        int result = 0;
        for (SampleDisVO param : params) {
            param.setAprReqDiv(ApproveRequestDivision.DISPOSE_SAMPLE.getCode());
            ApproveVO approveInfo = setApproveInfo(param);
            approveService.create(approveInfo);
            param.setSmpDpsProc(SampleDisposalProgress.APPROVE_DISPOSAL_CANCEL.getCode());
            param.setSmpDpsAprIdx(approveInfo.getAprIdx());
            result += dao.requestDis(param);
        }
        if (result != params.size()) {
            throw new NoUpdatedDataException();
        }
    }

    private ApproveVO setApproveInfo(SampleDisVO param) {
        ApproveVO approveInfo = new ApproveVO();
        approveInfo.setPlntCd(param.getPlntCd());
        approveInfo.setAprReqDiv(param.getAprReqDiv());
        approveInfo.setAprUid(param.getAprUid());
        approveInfo.setAprReqUid(param.getAprReqUid());
        return approveInfo;
    }
}
