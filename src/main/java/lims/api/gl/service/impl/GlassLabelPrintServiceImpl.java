package lims.api.gl.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.gl.dao.GlassLabelPrintDao;
import lims.api.gl.enums.GlassMaterialProcess;
import lims.api.gl.service.GlassLabelPrintService;
import lims.api.gl.vo.GlassMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GlassLabelPrintServiceImpl implements GlassLabelPrintService {

    private final GlassLabelPrintDao dao;

    @Override
    public List<GlassMaterialVO> findAll(GlassMaterialVO param) {
        return dao.findAll(param);
    }

    @Override
    public void update(GlassMaterialVO param) {
        if(!GlassMaterialProcess.WAREHOUSING.getProcessCode().equals(param.getMngProcCd())) {
            dao.reprint(param);
            return;
        }

        insertManageGlassMaterialsAfterPrinted(param);

        param.setEtrProcCd(GlassMaterialProcess.APPROVE_COMPLETE.getProcessCode());
        int result = dao.updateEtrState(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }

        param.setMngProcCd(GlassMaterialProcess.STOCK.getProcessCode());
        result += dao.updateMngState(param);

        if(result != param.getEtrQty() + 1) {
            throw new NoUpdatedDataException();
        }
    }

    public void insertManageGlassMaterialsAfterPrinted(GlassMaterialVO param) {
        int result = 0;
        int etrQty = param.getEtrQty();

        for(int i=0; i<etrQty; i++) {
            param.setRitmEtrNo(i+1);
            param.setMngProcCd(GlassMaterialProcess.WAREHOUSING.getProcessCode());
            result += dao.create(param);
        }

        if(etrQty != result) {
            throw new NoCreatedDataException();
        }
    }
}
