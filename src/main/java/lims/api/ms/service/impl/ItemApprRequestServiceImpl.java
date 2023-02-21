package lims.api.ms.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.ms.dao.ItemApprRequestDao;
import lims.api.ms.enums.SpecProgress;
import lims.api.ms.service.ItemApprRequestService;
import lims.api.ms.vo.ItemApprRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemApprRequestServiceImpl implements ItemApprRequestService {
    private final ItemApprRequestDao dao;
    private final ApproveService approveService;

    @Override
    public List<ItemApprRequestVO> find(ItemApprRequestVO param) {
        param.setSpecProcCd(SpecProgress.REQUEST_REVIEW.getCode());
        return dao.find(param);
    }

    @Override
    public void requestApprove(ItemApprRequestVO param) {
        ApproveVO approveInfo = setApproveVO(param);
        if (param.getPitmMstAprIdx() != null) {
            approveInfo.setAprIdx(param.getPitmMstAprIdx());
            approveService.update(approveInfo);
        } else {
            approveService.create(approveInfo);
        }
        param.setPitmMstAprIdx(approveInfo.getAprIdx());
        param.setSpecProcCd(SpecProgress.REQUEST_REVIEW.getCode());
        int result = 0;
        result += dao.updateRequestApprove(param);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void returnReview(ItemApprRequestVO param) {
        param.setSpecProcCd(SpecProgress.REVIEW_RETURN.getCode());
        int result = 0;
        result += dao.updateRequestApprove(param);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    private ApproveVO setApproveVO(ItemApprRequestVO param) {
        ApproveVO approveInfo = new ApproveVO();
        approveInfo.setPlntCd(param.getPlntCd());
        approveInfo.setAprReqDiv(param.getAprReqDiv());
        approveInfo.setAprReqUid(param.getAprUid());
        approveInfo.setAprReqIp(param.getAprIp());
        approveInfo.setAprReqRea(param.getAprReqRea());
        return approveInfo;
    }
}
