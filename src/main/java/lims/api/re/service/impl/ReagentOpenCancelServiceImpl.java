package lims.api.re.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.re.dao.ReagentOpenCancelDao;
import lims.api.re.enums.ReagentMaterialProcess;
import lims.api.re.service.ReagentOpenCancelService;
import lims.api.re.vo.ReagentMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReagentOpenCancelServiceImpl implements ReagentOpenCancelService {

    private final ReagentOpenCancelDao dao;

    @Override
    public List<ReagentMaterialVO> findAll(ReagentMaterialVO param) {
        param.setEtrProcCd(ReagentMaterialProcess.APPROVE_COMPLETE.getProcessCode());
        param.setMngProcCd(ReagentMaterialProcess.OPEN.getProcessCode());
        return dao.findAll(param);
    }

    @Override
    public void cancel(List<ReagentMaterialVO> list) {
        int result = 0;
        for(ReagentMaterialVO row : list) {
            row.setMngProcCd(ReagentMaterialProcess.STOCK.getProcessCode());
            result += dao.cancel(row);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }
}
