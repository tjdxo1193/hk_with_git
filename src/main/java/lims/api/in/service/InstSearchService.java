package lims.api.in.service;

import lims.api.in.vo.InstSearchVO;

import java.util.List;

public interface InstSearchService {
    List<InstSearchVO> find(InstSearchVO param);

    List<InstSearchVO> findHistory(InstSearchVO param);
}
