package lims.api.an.service.Impl;

import lims.api.an.dao.AnalColUsageHisDao;
import lims.api.an.enums.AnalColMaterialProcess;
import lims.api.an.service.AnalColUsageHisService;
import lims.api.an.vo.AnalColMaterialVO;
import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalColUsageHisServiceImpl implements AnalColUsageHisService {

    private final AnalColUsageHisDao dao;

    @Override
    public List<AnalColMaterialVO> findAll(AnalColMaterialVO param) {
        List processCodeList = new ArrayList();
        param.setEtrProcCd(AnalColMaterialProcess.APPROVE_COMPLETE.getProcessCode());

        if(StringUtils.isEmpty(param.getMngProcCd())) {
            processCodeList.add(AnalColMaterialProcess.OPEN.getProcessCode());
        }else {
            processCodeList.add(AnalColMaterialProcess.OPEN.getProcessCode());
            processCodeList.add(AnalColMaterialProcess.DISPOSAL.getProcessCode());
        }
        param.setProcessCodeList(processCodeList);

        return dao.findAll(param);
    }

    @Override
    public List<AnalColMaterialVO> findOpenItem(AnalColMaterialVO param) {
        param.setEtrProcCd(AnalColMaterialProcess.APPROVE_COMPLETE.getProcessCode());
        param.setMngProcCd(AnalColMaterialProcess.OPEN.getProcessCode());
        return dao.findOpenItem(param);
    }

    @Override
    public int create(AnalColMaterialVO param) {
        int result = dao.create(param);

        if(result == 2) {
            throw new NoCreatedDataException();
        }

        return param.getUseSeq();
    }

    @Override
    public void update(AnalColMaterialVO param) {
        int result = dao.update(param);

        if(result == 2) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void delete(AnalColMaterialVO param) {
        int result = dao.delete(param);

        if(result == 0) {
            throw new NoDeletedDataException();
        }
    }
}
