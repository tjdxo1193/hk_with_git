package lims.api.ms.service;

import lims.api.common.domain.PagingResult;
import lims.api.ms.vo.ItemManageVO;
import lims.api.ms.vo.MsElnCtRptFileVO;

import java.util.List;

public interface ItemManageService {
    PagingResult<ItemManageVO> findPItem(ItemManageVO param);

    List<ItemManageVO> findVersion(ItemManageVO param);

    void revision(ItemManageVO param);

    void tempSave(ItemManageVO param);

    void firstSave(ItemManageVO param);

    List<MsElnCtRptFileVO> getFileList(MsElnCtRptFileVO param);

    String savedFile(ItemManageVO param);

    List<ItemManageVO> getBomList(ItemManageVO param);
}
