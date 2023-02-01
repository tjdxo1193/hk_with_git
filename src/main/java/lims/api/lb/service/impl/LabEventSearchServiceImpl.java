package lims.api.lb.service.impl;

import lims.api.lb.dao.LabEventSearchDao;
import lims.api.lb.service.LabEventSearchService;
import lims.api.lb.vo.LabEventSearchVO;
import lims.api.ms.enums.ApproveRequestDivType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabEventSearchServiceImpl implements LabEventSearchService {

    private final LabEventSearchDao labEventSearchDao;

    @Override
    public List<LabEventSearchVO> findAll(LabEventSearchVO dto) {
        dto.setAprReqDiv(ApproveRequestDivType.LAB_EVENT_SPEC_VERSION.getCode());
        return labEventSearchDao.findAll(dto);
    }
}
