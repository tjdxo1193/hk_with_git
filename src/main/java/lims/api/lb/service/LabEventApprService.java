package lims.api.lb.service;

import lims.api.lb.vo.LabEventApprVO;
import lims.api.lb.vo.LabEventReviewVO;

import java.util.List;

public interface LabEventApprService {
    List<LabEventApprVO> findAll(LabEventApprVO dto);
    Integer approve(List<LabEventApprVO> dto);
    Integer reject(List<LabEventApprVO> dto);
}
