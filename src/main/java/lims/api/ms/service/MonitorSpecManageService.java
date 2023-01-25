package lims.api.ms.service;

import lims.api.common.vo.ComboVO;
import lims.api.ms.vo.MonitorSpecManageVO;

import java.util.List;

public interface MonitorSpecManageService {
    List<MonitorSpecManageVO> getMItemSpecList(MonitorSpecManageVO param);

    List<MonitorSpecManageVO> getVersionList(MonitorSpecManageVO param);

    List<MonitorSpecManageVO> getMItemSpecAItemList(MonitorSpecManageVO param);

    void createVersion(List<MonitorSpecManageVO> param);

    void createMItemSpecAItem(List<MonitorSpecManageVO> param);

    void deleteMItemSpec(MonitorSpecManageVO param);

    void updateRequestReview(MonitorSpecManageVO param);

    List<MonitorSpecManageVO> getItemMethodList(MonitorSpecManageVO param);

    List<ComboVO> getDepartmentList(MonitorSpecManageVO param);
}
