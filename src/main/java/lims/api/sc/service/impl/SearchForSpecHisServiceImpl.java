package lims.api.sc.service.impl;

import lims.api.ms.enums.SpecProgress;
import lims.api.ms.vo.SpecManageAitmVO;
import lims.api.ms.vo.SpecManageDptVO;
import lims.api.ms.vo.SpecManagePitmVO;
import lims.api.ms.vo.SpecManageVO;
import lims.api.sc.dao.SearchForSpecHisDao;
import lims.api.sc.service.SearchForSpecHisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class SearchForSpecHisServiceImpl implements SearchForSpecHisService {

    private final SearchForSpecHisDao dao;

    @Override
    public List<SpecManagePitmVO> getPItemList(SpecManageVO param) {
        param.setQpSpecProcCd(SpecProgress.APPROVED.getCode());
        return dao.getPItemList(param);
    }

    @Override
    public List<SpecManageVO> getVersionList(SpecManageVO param) {
        return dao.getVersionList(param);
    }

    @Override
    public List<SpecManageAitmVO> getAItemList(SpecManageVO param) {
        return dao.getAItemList(param);
    }

    @Override
    public List<SpecManageAitmVO> getSemiAItemList(SpecManageVO param) {
        return dao.getSemiAItemList(param);
    }

    @Override
    public List<SpecManageDptVO> getDepartmentList(SpecManageVO param) {
        return dao.getDepartmentList(param);
    }
}
