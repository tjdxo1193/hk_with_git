package lims.api.tp.service;

import lims.api.tp.vo.SampleManageVO;

import java.util.List;

public interface SampleManageService {
    List<SampleManageVO> find(SampleManageVO param);

    void create(SampleManageVO param);

    void update(SampleManageVO param);

    void delete(SampleManageVO param);

    List<SampleManageVO> findTest(SampleManageVO param);
}
