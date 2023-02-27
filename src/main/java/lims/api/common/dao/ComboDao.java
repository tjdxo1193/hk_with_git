package lims.api.common.dao;

import lims.api.common.vo.ComboSearchVO;
import lims.api.common.vo.ComboVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ComboDao {
    List<ComboVO> getDetailCommonCode(ComboSearchVO param);
    List<ComboVO> getUpperMenu();
    List<ComboVO> getUserAuth(String plntCd);
    List<ComboVO> getUpperTreeCd(ComboVO param);
    List<ComboVO> getUser(String plntCd);
    List<ComboVO> getTreeComboByParentCode(ComboSearchVO param);
    List<ComboVO> getDpt(String plntCd);
    List<ComboVO> getAnsCyl(String plntCd);
    List<ComboVO> getAmItmCd();
    List<ComboVO> getApproverList(String plntCd);
    List<ComboVO> getReviewerList(String plntCd);
    List<ComboVO> getPlntCd();
    List<ComboVO> getDptByLevel(ComboSearchVO param);
    List<ComboVO> getYearList();
	List<ComboVO> getTestMaterialTreeCd(String value, String plntCd);
    List<ComboVO> getTestTerm(String plntCd);
    List<ComboVO> getEquipment(String plntCd);
}
