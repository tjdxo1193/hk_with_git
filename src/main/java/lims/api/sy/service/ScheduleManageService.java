package lims.api.sy.service;

import lims.api.sy.vo.ScheduleManageVO;

import java.util.List;

public interface ScheduleManageService {
    List<ScheduleManageVO> getList(ScheduleManageVO param);
}
