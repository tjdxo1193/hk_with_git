package lims.api.an.service.Impl;

import lims.api.an.dao.AnalColOpenCancelDao;
import lims.api.an.enums.AnalColMaterialProcess;
import lims.api.an.service.AnalColOpenCancelService;
import lims.api.an.vo.AnalColMaterialVO;
import lims.api.common.exception.NoUpdatedDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalColOpenCancelServiceImpl implements AnalColOpenCancelService {

    private final AnalColOpenCancelDao dao;

    @Override
    public List<AnalColMaterialVO> findAll(AnalColMaterialVO param) {
        param.setEtrProcCd(AnalColMaterialProcess.APPROVE_COMPLETE.getProcessCode());
        param.setMngProcCd(AnalColMaterialProcess.OPEN.getProcessCode());
        return dao.findAll(param);
    }

    @Override
    public void cancel(AnalColMaterialVO param) {
        param.setMngProcCd(AnalColMaterialProcess.STOCK.getProcessCode());
        int result = dao.cancel(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}
