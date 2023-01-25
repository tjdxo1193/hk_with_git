package lims.api.an.service.Impl;

import lims.api.an.dao.AnalColLabelPrintDao;
import lims.api.an.enums.AnalColMaterialProcess;
import lims.api.an.service.AnalColLabelPrintService;
import lims.api.an.vo.AnalColMaterialVO;
import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.sd.enums.StandardMaterialProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalColLabelPrintServiceImpl implements AnalColLabelPrintService {

    private final AnalColLabelPrintDao dao;

    @Override
    public List<AnalColMaterialVO> findAll(AnalColMaterialVO param) {
        return dao.findAll(param);
    }

    @Override
    public void update(AnalColMaterialVO param) {
        if(!AnalColMaterialProcess.WAREHOUSING.getProcessCode().equals(param.getMngProcCd())) {
            dao.reprint(param);
            return;
        }

        insertManageAnalColMaterialsAfterPrinted(param);

        param.setEtrProcCd(AnalColMaterialProcess.APPROVE_COMPLETE.getProcessCode());
        int result = dao.updateEtrState(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }

        param.setMngProcCd(AnalColMaterialProcess.STOCK.getProcessCode());
        result += dao.updateMngState(param);

        if(result != 2) {
            throw new NoUpdatedDataException();
        }
    }

    public void insertManageAnalColMaterialsAfterPrinted(AnalColMaterialVO param) {
        param.setRitmEtrNo(1);
        param.setMngProcCd(AnalColMaterialProcess.WAREHOUSING.getProcessCode());
        int result = dao.create(param);

        if(result == 0) {
            throw new NoCreatedDataException();
        }
    }
}
