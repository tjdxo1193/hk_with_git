package lims.api.in.service;

import lims.api.in.vo.InstUsageHisVO;

import java.util.List;

public interface InstUsageHisService {
    List<InstUsageHisVO> find(InstUsageHisVO param);

    List<InstUsageHisVO> findInstrument(InstUsageHisVO param);

    void create(InstUsageHisVO param);

    void update(InstUsageHisVO param);

    void delete(InstUsageHisVO param);

    Integer updateFile(InstUsageHisVO param);
}
