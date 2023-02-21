package lims.api.sc.service;

import lims.api.ms.vo.SpecManageAitmVO;
import lims.api.ms.vo.SpecManageDptVO;
import lims.api.ms.vo.SpecManagePitmVO;
import lims.api.ms.vo.SpecManageVO;

import java.util.List;

public interface SearchForSpecHisService {
    List<SpecManagePitmVO> getPItemList(SpecManageVO param);

    List<SpecManageVO> getVersionList(SpecManageVO param);

    List<SpecManageAitmVO> getAItemList(SpecManageVO param);
    List<SpecManageAitmVO> getSemiAItemList(SpecManageVO param);

    List<SpecManageDptVO> getDepartmentList(SpecManageVO param);
}
