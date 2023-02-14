package lims.api.st.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.st.dao.StabSampleDisDao;
import lims.api.st.service.StabSampleDisService;
import lims.api.st.vo.StabSampleDisVO;
import lims.api.tp.enums.ApproveRequestDivision;
import lims.api.tp.enums.SampleDisposalProgress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StabSampleDisServiceImpl implements StabSampleDisService {
    private final StabSampleDisDao dao;
    private final ApproveService approveService;

    @Override
    public List<StabSampleDisVO> find(StabSampleDisVO param) {
        return dao.find(param);
    }

    @Override
    public void requestDispose(List<StabSampleDisVO> params) {
        int result = 0;
        for (StabSampleDisVO param : params) {
            param.setAprReqDiv(ApproveRequestDivision.DISPOSE_SAMPLE.getCode());
            ApproveVO approveInfo = setApproveInfo(param);
            approveService.create(approveInfo);
            param.setSmpDpsProc(SampleDisposalProgress.REQUEST_DISPOSAL.getCode());
            param.setSmpDpsAprIdx(approveInfo.getAprIdx());
            result += dao.requestDispose(param);
        }
        if (result != params.size()) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void requestCancelDispose(List<StabSampleDisVO> params) {
        int result = 0;
        for (StabSampleDisVO param : params) {
            param.setAprReqDiv(ApproveRequestDivision.DISPOSE_SAMPLE.getCode());
            ApproveVO approveInfo = setApproveInfo(param);
            approveService.create(approveInfo);
            param.setSmpDpsProc(SampleDisposalProgress.REQUEST_DISPOSAL_CANCEL.getCode());
            param.setSmpDpsAprIdx(approveInfo.getAprIdx());
            result += dao.requestCancelDispose(param);
        }
        if (result != params.size()) {
            throw new NoUpdatedDataException();
        }
    }

    private ApproveVO setApproveInfo(StabSampleDisVO param) {
        ApproveVO approveInfo = new ApproveVO();
        approveInfo.setPlntCd(param.getPlntCd());
        approveInfo.setAprReqDiv(param.getAprReqDiv());
        approveInfo.setAprUid(param.getAprUid());
        approveInfo.setAprReqUid(param.getAprReqUid());
        return approveInfo;
    }
}
