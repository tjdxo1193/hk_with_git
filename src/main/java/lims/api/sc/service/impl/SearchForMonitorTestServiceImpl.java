package lims.api.sc.service.impl;

import lims.api.sc.dao.SearchForMonitorTestDao;
import lims.api.sc.service.SearchForMonitorTestService;
import lims.api.sc.vo.SearchForMonitorTestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SearchForMonitorTestServiceImpl implements SearchForMonitorTestService {
    private final SearchForMonitorTestDao dao;

    @Override
    public List<SearchForMonitorTestVO> find(SearchForMonitorTestVO param) {
        return dao.find(param);
    }

    @Override
    public List<SearchForMonitorTestVO> findSpec(SearchForMonitorTestVO param) {
        return dao.findSpec(param);
    }
}
