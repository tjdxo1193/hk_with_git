package lims.api.sy.service.impl;

import lims.api.sy.dao.UserManageDao;
import lims.api.sy.service.UserManageService;
import lims.api.sy.vo.UserManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManageServiceImpl implements UserManageService {

    public static final String INITIAL_PWD = "1" ;
    private final UserManageDao dao;

    @Override
    public List<UserManageVO> findAll(UserManageVO vo) {
        return dao.findAll(vo);
    }

    @Override
    public int update(UserManageVO vo) {
        return dao.update(vo);
    }

    @Override
    public int initPwd(UserManageVO vo) {
        vo.setPwd(INITIAL_PWD);
        return dao.initPwd(vo);
    }
}