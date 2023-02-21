package lims.api.sm.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.sm.dao.SpecimenManageDao;
import lims.api.sm.service.SpecimenManageService;
import lims.api.sm.vo.SpecimenManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecimenManageServiceImpl implements SpecimenManageService {

    private final SpecimenManageDao dao;

    @Override
    public List<SpecimenManageVO> getSpecimenList(SpecimenManageVO request) {
        return dao.getSpecimenList(request);
    }

    @Override
    public List<SpecimenManageVO> getSpecimenHis(SpecimenManageVO request) {
        return dao.getSpecimenHis(request);
    }

    @Override
    public void save(SpecimenManageVO request) {
        int result;
        if (request.getSpcmIdx() == 0){
            result = dao.insertSpecimen(request);
        }else{
            result = dao.updateSpecimen(request);
            dao.insertSpecimenHis(request);
        }
        if (result == 0) {
            throw new NoUpdatedDataException();
        }

    }
}
