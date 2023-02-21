package lims.api.ms.service.impl;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.ms.dao.SpecApprRequestDao;
import lims.api.ms.enums.ApproveRequestDivType;
import lims.api.ms.enums.SpecProgress;
import lims.api.ms.service.SpecApprRequestService;
import lims.api.ms.vo.SpecApprRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpecApprRequestServiceImpl implements SpecApprRequestService {

    private final SpecApprRequestDao dao;

    private final ApproveService approveService;

    @Override
    public List<SpecApprRequestVO> getVersionList(SpecApprRequestVO param) {
        Set<String> codes = SpecProgress.getCodesRelatedToSpecProgress();
        String specProgressInClauseCondition = "'" +String.join("','", codes) + "'";
        param.setSpecProcCd(specProgressInClauseCondition);
        return dao.getVersionList(param);
    }

    @Override
    public List<SpecApprRequestVO> getAItemList(SpecApprRequestVO param) {
        return dao.getAItemList(param);
    }

    @Override
    public void updateReviewReturn(SpecApprRequestVO param) {
        int result = 1;
        param.setSpecProcCd(SpecProgress.REVIEW_RETURN.getCode());
        result = dao.updateReviewReturn(param);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void updateApprovalRequest(SpecApprRequestVO param) {
        int result = 1;
        param.setSpecProcCd(SpecProgress.APPROVAL_REQUEST.getCode());
        ApproveVO approveInfo = new ApproveVO();
        approveInfo.setAprReqDiv(ApproveRequestDivType.ITEM_TEST_SPEC_VERSION.getCode());
        approveInfo.setAprReqRea(param.getAprReqRea());
        approveInfo.setAprUid(param.getAprUid());
        approveInfo.setAprIdx(param.getPitmAnsSpecAprIdx());
        param.setPitmAnsSpecAprIdx(approveService.requestApprove(approveInfo));

        result = dao.updateApprovalRequest(param);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}
