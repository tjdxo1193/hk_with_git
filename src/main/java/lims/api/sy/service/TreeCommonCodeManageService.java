package lims.api.sy.service;

import lims.api.sy.vo.TreeCommonCodeManageVO;

import java.util.List;

public interface TreeCommonCodeManageService {

    List<TreeCommonCodeManageVO> getCodes(String plantCode);

    List<TreeCommonCodeManageVO> getCodesByCode(String plantCode, String treeCode, TreeCommonCodeManageVO param);

    void saveNode(String planCode, List<TreeCommonCodeManageVO> addedList, List<TreeCommonCodeManageVO> updatedList);

    void createNode(String plantCode, List<TreeCommonCodeManageVO> params);

    void updateNode(String plantCode, List<TreeCommonCodeManageVO> params);

}