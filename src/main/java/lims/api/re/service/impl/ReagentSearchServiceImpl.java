package lims.api.re.service.impl;

import lims.api.re.dao.ReagentSearchDao;
import lims.api.re.enums.ReagentMaterialProcess;
import lims.api.re.service.ReagentSearchService;
import lims.api.re.vo.ReagentMaterialVO;
import lims.api.sd.enums.StandardMaterialProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReagentSearchServiceImpl implements ReagentSearchService {

    private final ReagentSearchDao dao;

    @Override
    public List<ReagentMaterialVO> findAll(ReagentMaterialVO param) {
        List processCodeList = new ArrayList();
        param.setEtrProcCd(StandardMaterialProcess.APPROVE_COMPLETE.getProcessCode());
        processCodeList.add(0, ReagentMaterialProcess.OPEN.getProcessCode());
        processCodeList.add(1, ReagentMaterialProcess.DISPOSAL.getProcessCode());
        param.setProcessCodeList(processCodeList);
        return dao.findAll(param);
    }

    @Override
    public List<ReagentMaterialVO> findAllByRitmEtrIdx(Integer ritmEtrIdx) {
        return dao.findAllByRitmEtrIdx(ritmEtrIdx);
    }

    @Override
    public List<ReagentMaterialVO> findAllByRitmMngIdx(Integer ritmEtrIdx, Integer ritmMngIdx, String delYn) {
        return dao.findAllByRitmMngIdx(ritmEtrIdx, ritmMngIdx, delYn);
    }
}
