package lims.api.sc.service.impl;

import lims.api.sc.dao.SearchForStabTestDao;
import lims.api.sc.service.SearchForStabTestService;
import lims.api.sc.vo.SearchForStabTestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchForStabTestServiceImpl implements SearchForStabTestService {
    private final SearchForStabTestDao dao;

    @Override
    public List<SearchForStabTestVO> find(SearchForStabTestVO param) {
        return dao.find(param);
    }

    @Override
    public List<SearchForStabTestVO> findSpec(SearchForStabTestVO param) {
        return dao.findSpec(param);
    }
}
