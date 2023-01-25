package lims.api.sy.service;

import lims.api.sy.vo.LoginHistoryVO;

import java.util.List;

public interface LoginHistoryService {
    List<LoginHistoryVO> find(LoginHistoryVO param);
}
