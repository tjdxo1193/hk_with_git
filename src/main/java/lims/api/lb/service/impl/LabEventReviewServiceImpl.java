package lims.api.lb.service.impl;

import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.lb.dao.LabEventReviewDao;
import lims.api.lb.service.LabEventReviewService;
import lims.api.lb.vo.LabEventReviewVO;
import lims.api.ms.enums.ApproveRequestDivType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabEventReviewServiceImpl implements LabEventReviewService {

    private final LabEventReviewDao labEventReviewDao;
    private final ApproveService approveService;

    @Override
    public List<LabEventReviewVO> findAll(LabEventReviewVO dto) {
        return labEventReviewDao.findAll(dto);
    }

    @Override
    public Integer approveRequest(List<LabEventReviewVO> dto) {
        int result = 0;
        for(LabEventReviewVO item : dto) {
            int labEvtAprIdx = this.requestApprove(item);
            item.setLabEvtAprIdx(labEvtAprIdx);
            result += labEventReviewDao.approveRequest(item);
        }

        return result;
    }

    @Override
    public Integer reject(List<LabEventReviewVO> dto) {
        int result = 0;
        for(LabEventReviewVO item : dto) {
            int aprIdx = this.createApprove(item);
            item.setLabEvtAprIdx(aprIdx);
            result += labEventReviewDao.reject(item);
        }

        return result;
    }

    public int createApprove(LabEventReviewVO param) {
        ApproveVO approveVO = new ApproveVO();
        approveVO.setPlntCd(param.getPlntCd());
        approveVO.setAprReqDiv(ApproveRequestDivType.LAB_EVENT_SPEC_VERSION.getCode());
        approveVO.setAprReqUid(param.getAnsUid());
        approveVO.setAprReqIp(param.getLoginUserIp());
        approveVO.setAprUid(param.getLoginUserUid());

        approveService.create(approveVO);

        return approveVO.getAprIdx();
    }

    public int requestApprove(LabEventReviewVO param) {
        param.getApproveInfo().setAprIdx(param.getLabEvtAprIdx());
        param.getApproveInfo().setAprReqDiv(ApproveRequestDivType.LAB_EVENT_SPEC_VERSION.getCode());
        return approveService.requestApprove(param.getApproveInfo());
    }
}
