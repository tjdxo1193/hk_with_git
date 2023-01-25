package lims.api.sd.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.sd.dao.StdItemOpenCancelDao;
import lims.api.sd.enums.StandardMaterialProcess;
import lims.api.sd.service.StdItemOpenCancelService;
import lims.api.sd.vo.StandardMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StdItemOpenCancelServiceImpl implements StdItemOpenCancelService {

    private final StdItemOpenCancelDao dao;

    @Override
    public List<StandardMaterialVO> findAll(StandardMaterialVO param) {
        param.setEtrProcCd(StandardMaterialProcess.APPROVE_COMPLETE.getProcessCode());
        param.setMngProcCd(StandardMaterialProcess.OPEN.getProcessCode());
        return dao.findAll(param);
    }

    @Override
    public void cancel(List<StandardMaterialVO> list) {
        int result = 0;
        for(StandardMaterialVO row : list) {
            row.setMngProcCd(StandardMaterialProcess.STOCK.getProcessCode());
            result += dao.cancel(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }
}
