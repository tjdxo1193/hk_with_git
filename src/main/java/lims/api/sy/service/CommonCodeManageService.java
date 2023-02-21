package lims.api.sy.service;

import lims.api.sy.vo.CommonCodeVO;
import lims.api.sy.vo.CommonDetailCodeVO;

import java.util.List;

public interface CommonCodeManageService {
    List<CommonCodeVO> findUpperCode(CommonCodeVO param);
    List<CommonDetailCodeVO> findDetailCode(CommonDetailCodeVO param);
    void upperCodeInsert(CommonCodeVO param, String plntCd);
    void upperCodeUpdate(CommonCodeVO param, String plntCd);
    void upperDetailCodeInsert(CommonDetailCodeVO param, String plntCd);
    void upperDetailCodeUpdate(CommonDetailCodeVO param, String plntCd);
}