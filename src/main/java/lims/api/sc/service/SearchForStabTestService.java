package lims.api.sc.service;

import lims.api.sc.vo.SearchForStabTestVO;

import java.util.List;

public interface SearchForStabTestService {
    List<SearchForStabTestVO> find(SearchForStabTestVO param);

    List<SearchForStabTestVO> findSpec(SearchForStabTestVO param);
}
