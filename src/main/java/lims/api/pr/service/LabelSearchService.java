package lims.api.pr.service;

import lims.api.pr.vo.LabelSearchVO;

import java.util.List;

public interface LabelSearchService {
    // 라벨출력조회 조회
    List<LabelSearchVO> findAll(LabelSearchVO param);
}
