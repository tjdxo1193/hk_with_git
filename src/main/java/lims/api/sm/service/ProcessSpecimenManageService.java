package lims.api.sm.service;

import lims.api.sm.vo.ProcessSpecimenManageVO;

import java.util.List;

public interface ProcessSpecimenManageService {
    List<ProcessSpecimenManageVO> getProcessSpecimenList(ProcessSpecimenManageVO request);

    void save(ProcessSpecimenManageVO request);
}
