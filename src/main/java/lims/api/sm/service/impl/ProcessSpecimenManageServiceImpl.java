package lims.api.sm.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.sm.dao.ProcessSpecimenManageDao;
import lims.api.sm.service.ProcessSpecimenManageService;
import lims.api.sm.vo.ProcessSpecimenManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessSpecimenManageServiceImpl implements ProcessSpecimenManageService {

    private final ProcessSpecimenManageDao dao;

    @Override
    public List<ProcessSpecimenManageVO> getProcessSpecimenList(ProcessSpecimenManageVO request) {
        return dao.getProcessSpecimenList(request);
    }

    @Override
    public void save(ProcessSpecimenManageVO request) {
        int result;
        if (request.getPsSpcmIdx() == 0){
            result = dao.insertProcessSpecimen(request);
        }else{
            result = dao.updateProcessSpecimen(request);
        }
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}
