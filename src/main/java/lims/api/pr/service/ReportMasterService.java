package lims.api.pr.service;

import lims.api.pr.vo.ReportMasterVO;

import java.util.List;

public interface ReportMasterService {
    List<ReportMasterVO> findAll(ReportMasterVO reportMasterVO);

    int save(ReportMasterVO reportMasterVO);

    int delete(ReportMasterVO reportMasterVO);
}
