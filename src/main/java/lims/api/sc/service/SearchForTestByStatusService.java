package lims.api.sc.service;

import lims.api.sc.vo.SearchForTestByStatusVO;

import java.util.List;

public interface SearchForTestByStatusService {
    List<SearchForTestByStatusVO> find(SearchForTestByStatusVO param);

    List<SearchForTestByStatusVO> findSpec(SearchForTestByStatusVO param);
}
