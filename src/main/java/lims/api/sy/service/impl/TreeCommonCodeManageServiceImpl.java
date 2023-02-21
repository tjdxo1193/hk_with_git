package lims.api.sy.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.sy.dao.TreeCommonCodeManageDao;
import lims.api.sy.service.TreeCommonCodeManageService;
import lims.api.sy.vo.TreeCommonCodeManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreeCommonCodeManageServiceImpl implements TreeCommonCodeManageService {

    private final String ROOT_CODE_CONTAIN_TOKEN = "0000000";
    private final TreeCommonCodeManageDao commonCodeManageDao;

    @Override
    public List<TreeCommonCodeManageVO> getCodes(String plantCode) {
        return commonCodeManageDao.findAll(plantCode);
    }

    @Override
    public List<TreeCommonCodeManageVO> getCodesByCode(String plantCode, String treeCode, TreeCommonCodeManageVO param) {
        param.setPlntCd(plantCode);
        param.setTreeCd(treeCode);
        return commonCodeManageDao.findByCode(param);
    }

    @Override
    public void saveNode(String planCode, List<TreeCommonCodeManageVO> addedList, List<TreeCommonCodeManageVO> updatedList) {
        this.createNode(planCode, addedList);
        this.updateNode(planCode, updatedList);
    }

    @Override
    public void createNode(String plantCode, List<TreeCommonCodeManageVO> params) {
        if (CollectionUtils.isEmpty(params)) {
            return;
        }

        int result = 0;

        for (TreeCommonCodeManageVO param : params) {
            String treeCode = this.isRootCode(param.getHirTreeCd()) ?
                    commonCodeManageDao.getNextChildCodeByRoot(param.getHirTreeCd()) :
                    commonCodeManageDao.getNextChildCode(param.getHirTreeCd());

            param.setPlntCd(plantCode);
            param.setTreeCd(treeCode);
            result += commonCodeManageDao.createNode(param);
        }

        if (result == 0) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void updateNode(String plantCode, List<TreeCommonCodeManageVO> params) {
        if (CollectionUtils.isEmpty(params)) {
            return;
        }

        int result = 0;

        for (TreeCommonCodeManageVO param : params) {
            param.setPlntCd(plantCode);
            result += commonCodeManageDao.updateNode(param);
        }

        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    private boolean isRootCode(String code) {
        return code != null && code.contains(ROOT_CODE_CONTAIN_TOKEN);
    }
}