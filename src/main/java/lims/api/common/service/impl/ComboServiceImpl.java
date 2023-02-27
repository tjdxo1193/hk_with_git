package lims.api.common.service.impl;

import lims.api.common.dao.ComboDao;
import lims.api.common.service.ComboService;
import lims.api.common.vo.ComboSearchVO;
import lims.api.common.vo.ComboVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComboServiceImpl implements ComboService {

    private final ComboDao dao;

    @Override
    public List<ComboVO> getDetailCommonCode(ComboSearchVO param) {
        return dao.getDetailCommonCode(param);
    }

    @Override
    public List<ComboVO> getUpperMenu() {
        return dao.getUpperMenu();
    }

    @Override
    public List<ComboVO> getUserAuth(String plntCd) {
        return dao.getUserAuth(plntCd);
    }

    @Override
    public List<ComboVO> getUpperTreeCd(ComboVO param) {
        return dao.getUpperTreeCd(param);
    }

    @Override
    public List<ComboVO> getTreeComboByParentCode(ComboSearchVO param) {
        return dao.getTreeComboByParentCode(param);
    }

    @Override
    public List<ComboVO> getUser(String plntCd) {
        return dao.getUser(plntCd);
    }

    @Override
    public List<ComboVO> getDpt(String plntCd) {
        return dao.getDpt(plntCd);
    }

    @Override
    public List<ComboVO> getAnsCyl(String plntCd) {
        return dao.getAnsCyl(plntCd);
    }

    @Override
    public List<ComboVO> getAmItmCd() {
        return dao.getAmItmCd();
    }

    @Override
    public List<ComboVO> getApproverList(String plntCd) {
        return dao.getApproverList(plntCd);
    }

    @Override
    public List<ComboVO> getReviewerList(String plntCd) {
        return dao.getReviewerList(plntCd);
    }

    @Override
    public List<ComboVO> getPlntCd() {
        return dao.getPlntCd();
    }

    @Override
    public List<ComboVO> getDptByLevel(ComboSearchVO param) {
        return dao.getDptByLevel(param);
    }

    @Override
    public List<ComboVO> getYearList() {
        return dao.getYearList();
    }

    @Override
	public List<ComboVO> getTestMaterialTreeCd(ComboVO param, String plntCd) {
		return dao.getTestMaterialTreeCd(param.getValue(), plntCd);
	}

    @Override
    public List<ComboVO> getTestTerm(String plntCd) {
        return dao.getTestTerm(plntCd);
    }

    @Override
    public List<ComboVO> getEquipment(String plntCd) {
        return dao.getEquipment(plntCd);
    }
}
