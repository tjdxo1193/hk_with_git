package lims.api.ms.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.ms.dao.MonitorSpecApprRequestDao;
import lims.api.ms.enums.ApproveRequestDivType;
import lims.api.ms.enums.SpecProgress;
import lims.api.ms.service.MonitorSpecApprRequestService;
import lims.api.ms.vo.MonitorSpecApprRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MonitorSpecApprRequestServiceImpl implements MonitorSpecApprRequestService {

    private final MonitorSpecApprRequestDao dao;
    private final ApproveService approveService;

    @Override
    public List<MonitorSpecApprRequestVO> getVersionList(MonitorSpecApprRequestVO param) {
        Set<String> codes = SpecProgress.getCodesRelatedToSpecProgress();
        String specProgressInClauseCondition  = "'" +String.join("','", codes) + "'";
        param.setSpecProcCd(specProgressInClauseCondition);
        return dao.getVersionList(param);
    }

    @Override
    public List<MonitorSpecApprRequestVO> getAItemList(MonitorSpecApprRequestVO param) {
        return dao.getAItemList(param);
    }

    @Override
    public void updateApprovalRequest(MonitorSpecApprRequestVO param) {
        int result = 1;
        param.setSpecProcCd(SpecProgress.APPROVAL_REQUEST.getCode());
        ApproveVO approveInfo = new ApproveVO();
        approveInfo.setAprReqDiv(ApproveRequestDivType.MONITORING_SPEC_VERSION.getCode());
        approveInfo.setAprReqRea(param.getAprReqRea());
        approveInfo.setAprUid(param.getAprUid());
        approveInfo.setAprIdx(param.getMitmSpecAprIdx());

        param.setMitmSpecAprIdx(approveService.requestApprove(approveInfo));
        approveInfo.setAprReqRea(param.getAprReqRea());

        result = dao.updateApprovalRequest(param);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }
    @Override
    public void updateReviewReturn(MonitorSpecApprRequestVO param) {
        int result = 1;
        param.setSpecProcCd(SpecProgress.REVIEW_RETURN.getCode());
        result = dao.updateReviewReturn(param);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}
