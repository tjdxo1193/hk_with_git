package lims.api.sy.service;

import lims.api.sy.vo.AuthManageVO;

import java.util.List;

public interface AuthManageService {
    List<AuthManageVO> findAllAth(AuthManageVO param);

    void insert(AuthManageVO param, String plntCd);

    void update(AuthManageVO param, String plntCd);

    List<AuthManageVO> findAthGp(AuthManageVO param);

    List<AuthManageVO> findNotAthGp(AuthManageVO param);

    void insertAthGp(AuthManageVO param, String plntCd);

    void deleteAthGp(AuthManageVO param, String plntCd);
}
