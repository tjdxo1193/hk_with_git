package lims.api.ms.service;

import lims.api.ms.vo.MonitorManageVO;

import java.util.List;

public interface MonitorManageService {
    List<MonitorManageVO> find(MonitorManageVO param);
    void insert(MonitorManageVO param);
    void update(MonitorManageVO param);
    void delete(MonitorManageVO param);
}
