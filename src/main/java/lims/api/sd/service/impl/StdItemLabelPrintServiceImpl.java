package lims.api.sd.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.sd.dao.StdItemLabelPrintDao;
import lims.api.sd.enums.StandardMaterialProcess;
import lims.api.sd.service.StdItemLabelPrintService;
import lims.api.sd.vo.StandardMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StdItemLabelPrintServiceImpl implements StdItemLabelPrintService {

    private final StdItemLabelPrintDao dao;

    @Override
    public List<StandardMaterialVO> findAll(StandardMaterialVO param) {
        param.setEtrProcCd(StandardMaterialProcess.APPROVE_COMPLETE.getProcessCode());
        return dao.findAll(param);
    }

    @Override
    public void update(StandardMaterialVO param) {
        if(!StandardMaterialProcess.WAREHOUSING.getProcessCode().equals(param.getMngProcCd())) {
            dao.reprint(param);
            return;
        }

        param.setMngProcCd(StandardMaterialProcess.STOCK.getProcessCode());
        int result = dao.update(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}
