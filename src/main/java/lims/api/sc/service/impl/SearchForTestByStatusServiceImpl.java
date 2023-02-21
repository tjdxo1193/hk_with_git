package lims.api.sc.service.impl;

import lims.api.sc.dao.SearchForTestByStatusDao;
import lims.api.sc.service.SearchForTestByStatusService;
import lims.api.sc.vo.SearchForTestByStatusVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchForTestByStatusServiceImpl implements SearchForTestByStatusService {
    private final SearchForTestByStatusDao dao;

    @Override
    public List<SearchForTestByStatusVO> find(SearchForTestByStatusVO param) {
        return dao.find(param);
    }

    @Override
    public List<SearchForTestByStatusVO> findSpec(SearchForTestByStatusVO param) {
        return dao.findSpec(param);
    }
}
