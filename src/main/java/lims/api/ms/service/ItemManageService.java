package lims.api.ms.service;

import lims.api.common.domain.PagingResult;
import lims.api.ms.vo.ItemManageVO;
import lims.api.ms.vo.MsElnCtRptFileVO;

import java.util.List;

public interface ItemManageService {
    PagingResult<ItemManageVO> findPItem(ItemManageVO param);

    List<ItemManageVO> findVersion(ItemManageVO param);

    void revision(ItemManageVO request);

    void tempSave(ItemManageVO request);

    void firstSave(ItemManageVO request);

    List<MsElnCtRptFileVO> getFileList(String ctrptNo);

    String savedFile(ItemManageVO request);

    List<ItemManageVO> getBomList(ItemManageVO request);
}
