package lims.api.ms.service;

import lims.api.ms.vo.SpecManageAitmVO;
import lims.api.ms.vo.SpecManageDptVO;
import lims.api.ms.vo.SpecManagePitmVO;
import lims.api.ms.vo.SpecManageVO;

import java.util.List;

public interface SpecManageService {

    List<SpecManagePitmVO> getPItemList(SpecManageVO param);

    List<SpecManageVO> getVersionList(SpecManageVO param);

    List<SpecManageAitmVO> getAItemList(SpecManageVO param);

    List<SpecManageAitmVO> getSemiAItemList(SpecManageVO param);

    List<SpecManageVO> getItemMethodList(SpecManageVO param);

    List<SpecManageDptVO> getDepartmentList(SpecManageVO param);

    void updateVersion(SpecManageVO param);

    void makeAItem(SpecManageVO param);

    void updateRequestReview(SpecManageVO param);

    List<SpecManagePitmVO> getPItemSpecListToModal(SpecManageVO param);

    List<SpecManageAitmVO> getAItemListToModal(SpecManageVO param);

    List<SpecManagePitmVO> getPackagingItemListToModal(SpecManageVO param);
}
