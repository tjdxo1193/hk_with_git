package lims.api.sy.service;

import lims.api.sy.vo.UserManageVO;

import java.util.List;

public interface UserManageService {
    List<UserManageVO> findAll(UserManageVO vo);
    int update(UserManageVO vo);

    int initPwd(UserManageVO vo);
}