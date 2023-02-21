package lims.api.common.service;

import lims.api.common.vo.ComboSearchVO;
import lims.api.common.vo.ComboVO;

import java.util.List;

public interface ComboService {
    List<ComboVO> getDetailCommonCode(ComboSearchVO param);
    List<ComboVO> getUpperMenu();
    List<ComboVO> getUserAuth(String plntCd);
    List<ComboVO> getUpperTreeCd(ComboVO param);
    List<ComboVO> getTreeComboByParentCode(ComboSearchVO param);
    List<ComboVO> getUser(String plntCd);
    List<ComboVO> getDpt(String plntCd);
    List<ComboVO> getAnsCyl();
    List<ComboVO> getAmItmCd();
    List<ComboVO> getApproverList(String plntCd);
    List<ComboVO> getReviewerList(String plntCd);
    List<ComboVO> getPlntCd();
    List<ComboVO> getDptByLevel(ComboSearchVO param);
    List<ComboVO> getYearList();
	List<ComboVO> getTestMaterialTreeCd(ComboVO param, String plntCd);
    List<ComboVO> getTestTerm(String plntCd);
    List<ComboVO> getEquipment(String plntCd);
}
