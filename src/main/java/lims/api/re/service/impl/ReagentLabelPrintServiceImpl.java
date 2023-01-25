package lims.api.re.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.re.dao.ReagentLabelPrintDao;
import lims.api.re.enums.ReagentMaterialProcess;
import lims.api.re.service.ReagentLabelPrintService;
import lims.api.re.vo.ReagentMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReagentLabelPrintServiceImpl implements ReagentLabelPrintService {

    private final ReagentLabelPrintDao dao;

    @Override
    public List<ReagentMaterialVO> findAll(ReagentMaterialVO param) {
        return dao.findAll(param);
    }

    @Override
    public void update(ReagentMaterialVO param) {
        if(!ReagentMaterialProcess.WAREHOUSING.getProcessCode().equals(param.getMngProcCd())) {
            dao.reprint(param);
            return;
        }

        insertManageReagentMaterialsAfterPrinted(param);

        param.setEtrProcCd(ReagentMaterialProcess.APPROVE_COMPLETE.getProcessCode());
        int result = dao.updateEtrState(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }

        param.setMngProcCd(ReagentMaterialProcess.STOCK.getProcessCode());
        result += dao.updateMngState(param);

        if(result != param.getEtrQty() + 1) {
            throw new NoUpdatedDataException();
        }
    }

    public void insertManageReagentMaterialsAfterPrinted(ReagentMaterialVO param) {
        int result = 0;
        int etrQty = param.getEtrQty();

        for(int i=0; i<etrQty; i++) {
            param.setRitmEtrNo(i+1);
            param.setMngProcCd(ReagentMaterialProcess.WAREHOUSING.getProcessCode());
            result += dao.create(param);
        }

        if(etrQty != result) {
            throw new NoCreatedDataException();
        }
    }
}
