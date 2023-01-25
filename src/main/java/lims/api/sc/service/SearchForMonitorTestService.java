package lims.api.sc.service;

import lims.api.sc.vo.SearchForMonitorTestVO;

import java.util.List;

public interface SearchForMonitorTestService {
    List<SearchForMonitorTestVO> find(SearchForMonitorTestVO param);

    List<SearchForMonitorTestVO> findSpec(SearchForMonitorTestVO param);
}
