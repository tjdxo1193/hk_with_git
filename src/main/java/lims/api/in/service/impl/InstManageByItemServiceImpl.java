package lims.api.in.service.impl;

import lims.api.in.dao.InstManageByItemDao;
import lims.api.in.service.InstManageByItemService;
import lims.api.in.vo.InstManageByItemVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstManageByItemServiceImpl implements InstManageByItemService {
    private final InstManageByItemDao dao;

    @Override
    public List<InstManageByItemVO> findItem(InstManageByItemVO param) {
        return dao.findItem(param);
    }

    @Override
    public List<InstManageByItemVO> findDetail(InstManageByItemVO param) {
        return dao.findDetail(param);
    }
}