package lims.api.sy.service.impl;

import lims.api.sy.dao.LoginHistoryDao;
import lims.api.sy.service.LoginHistoryService;
import lims.api.sy.vo.LoginHistoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginHistoryServiceImpl implements LoginHistoryService {
    private final LoginHistoryDao dao;

    @Override
    public List<LoginHistoryVO> find(LoginHistoryVO param) {
        return dao.find(param);
    }
}
