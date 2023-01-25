package lims.api.gl.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.gl.dao.GlassOpenDisDao;
import lims.api.gl.enums.GlassMaterialProcess;
import lims.api.gl.service.GlassOpenDisService;
import lims.api.gl.vo.GlassMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GlassOpenDisServiceImpl implements GlassOpenDisService {

    private final GlassOpenDisDao dao;

    @Override
    public List<GlassMaterialVO> findAll(GlassMaterialVO param) {
        List processCodeList = new ArrayList();
        param.setEtrProcCd(GlassMaterialProcess.APPROVE_COMPLETE.getProcessCode());

        if(GlassMaterialProcess.STOCK.getProcessCode().equals(param.getMngProcCd())) {
            processCodeList.add(GlassMaterialProcess.STOCK.getProcessCode());
            param.setProcessCodeList(processCodeList);
        }

        if(GlassMaterialProcess.OPEN.getProcessCode().equals(param.getMngProcCd())) {
            processCodeList.add(GlassMaterialProcess.STOCK.getProcessCode());
            processCodeList.add(GlassMaterialProcess.OPEN.getProcessCode());
            param.setProcessCodeList(processCodeList);
        }

        return dao.findAll(param);
    }

    @Override
    public void open(List<GlassMaterialVO> list) {
        int result = 0;
        for(GlassMaterialVO row : list) {
            row.setMngProcCd(GlassMaterialProcess.OPEN.getProcessCode());
            result += dao.open(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void disposal(List<GlassMaterialVO> list) {
        int result = 0;

        for(GlassMaterialVO row : list) {
            row.setMngProcCd(GlassMaterialProcess.DISPOSAL.getProcessCode());
            result += dao.disposal(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }
}
