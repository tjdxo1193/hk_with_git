package lims.api.gl.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.gl.dao.GlassOpenCancelDao;
import lims.api.gl.enums.GlassMaterialProcess;
import lims.api.gl.service.GlassOpenCancelService;
import lims.api.gl.vo.GlassMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GlassOpenCancelServiceImpl implements GlassOpenCancelService {

    private final GlassOpenCancelDao dao;

    @Override
    public List<GlassMaterialVO> findAll(GlassMaterialVO param) {
        param.setEtrProcCd(GlassMaterialProcess.APPROVE_COMPLETE.getProcessCode());
        param.setMngProcCd(GlassMaterialProcess.OPEN.getProcessCode());
        return dao.findAll(param);
    }

    @Override
    public void cancel(List<GlassMaterialVO> list) {
        int result = 0;
        for(GlassMaterialVO row : list) {
            row.setMngProcCd(GlassMaterialProcess.STOCK.getProcessCode());
            result += dao.cancel(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }
}
