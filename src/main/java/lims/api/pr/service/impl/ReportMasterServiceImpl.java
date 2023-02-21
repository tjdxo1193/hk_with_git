package lims.api.pr.service.impl;

import lims.api.pr.dao.ReportMasterDao;
import lims.api.pr.service.ReportMasterService;
import lims.api.pr.vo.ReportMasterVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportMasterServiceImpl implements ReportMasterService {
    private final ReportMasterDao reportMasterDao;

    @Override
    public List<ReportMasterVO> findAll(ReportMasterVO reportMasterVO) {
        return reportMasterDao.findAll(reportMasterVO);
    }

    @Override
    public int save(ReportMasterVO reportMasterVO) {
        if(reportMasterVO.getRptIdx() != null) {
            return reportMasterDao.update(reportMasterVO);
        }
        
        return reportMasterDao.create(reportMasterVO);
    }

    @Override
    public int delete(ReportMasterVO reportMasterVO) {
        return reportMasterDao.delete(reportMasterVO);
    }
}
