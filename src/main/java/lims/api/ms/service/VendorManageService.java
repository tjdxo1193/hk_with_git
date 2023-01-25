package lims.api.ms.service;

import lims.api.ms.vo.VendorVO;

import java.util.List;

public interface VendorManageService {
    List<VendorVO> findAll(VendorVO param);
    void create(VendorVO param);
    void update(VendorVO param);
    void delete(VendorVO param);
}
