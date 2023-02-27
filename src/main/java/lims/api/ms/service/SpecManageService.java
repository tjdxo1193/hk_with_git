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

    void createFirstVersion(SpecManageVO param);

    void saveAItemList(SpecManageVO param);

    void createNewVersion(SpecManageVO param);

    void updateNewVersion(SpecManageVO param);

    void updateRequestReview(SpecManageVO param);

    List<SpecManagePitmVO> getPItemSpecListToModal(SpecManageVO param);

    List<SpecManageAitmVO> getAItemListToModal(SpecManageVO param);

    List<SpecManagePitmVO> getPackagingItemListToModal(SpecManageVO param);

    List<SpecManageVO> getSemiPItemListToModal(SpecManageVO param);

    List<SpecManageAitmVO> getSemiAItemListToModal(SpecManageVO param);

    int putPkgaCd(SpecManageVO param);
}
