package lims.api.sd.service.impl;

import lims.api.sd.dao.StdItemSearchDao;
import lims.api.sd.enums.StandardMaterialProcess;
import lims.api.sd.service.StdItemSearchService;
import lims.api.sd.vo.StandardMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StdItemSearchServiceImpl implements StdItemSearchService {

    private final StdItemSearchDao dao;

    @Override
    public List<StandardMaterialVO> findAll(StandardMaterialVO param) {
        List processCodeList = new ArrayList();
        param.setEtrProcCd(StandardMaterialProcess.APPROVE_COMPLETE.getProcessCode());
        processCodeList.add(0, StandardMaterialProcess.OPEN.getProcessCode());
        processCodeList.add(1, StandardMaterialProcess.DISPOSAL.getProcessCode());
        param.setProcessCodeList(processCodeList);
        return dao.findAll(param);
    }

    @Override
    public List<StandardMaterialVO> findAllByRitmEtrIdx(Integer ritmEtrIdx) {
        return dao.findAllByRitmEtrIdx(ritmEtrIdx);
    }

    @Override
    public List<StandardMaterialVO> findAllByRitmMngIdx(Integer ritmEtrIdx, Integer ritmMngIdx, String delYn) {
        return dao.findAllByRitmMngIdx(ritmEtrIdx, ritmMngIdx, delYn);
    }
}
