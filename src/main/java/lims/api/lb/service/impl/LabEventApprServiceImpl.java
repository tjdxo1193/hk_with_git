package lims.api.lb.service.impl;

import lims.api.common.service.ApproveService;
import lims.api.lb.dao.LabEventApprDao;
import lims.api.lb.service.LabEventApprService;
import lims.api.lb.vo.LabEventApprVO;
import lims.api.ms.enums.ApproveRequestDivType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabEventApprServiceImpl implements LabEventApprService {

    private final LabEventApprDao labEventApprDao;
    private final ApproveService approveService;

    @Override
    public List<LabEventApprVO> findAll(LabEventApprVO dto) {
        dto.setAprReqDiv(ApproveRequestDivType.LAB_EVENT_SPEC_VERSION.getCode());
        return labEventApprDao.findAll(dto);
    }

    @Override
    public Integer approve(List<LabEventApprVO> dto) {
        int result = 0;
        for(LabEventApprVO item : dto) {
            this.approve(item);
            result += labEventApprDao.approve(item);
        }

        return result;
    }

    public void approve(LabEventApprVO param) {
        approveService.approve(param.getLabEvtAprIdx());
    }

    @Override
    public Integer reject(List<LabEventApprVO> dto) {
        int result = 0;
        for(LabEventApprVO item : dto) {
            result += labEventApprDao.reject(item);
        }

        return result;
    }
}