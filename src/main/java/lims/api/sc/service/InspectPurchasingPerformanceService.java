package lims.api.sc.service;

import lims.api.sc.vo.InspectPurchasingPerformanceVO;

import java.util.List;

public interface InspectPurchasingPerformanceService {
    List<InspectPurchasingPerformanceVO> find(InspectPurchasingPerformanceVO param);
    List<InspectPurchasingPerformanceVO> findDetail(String plntCd, String pitmCd, String phsOrderNo);
    List<InspectPurchasingPerformanceVO> getRecord(Integer ispPhsPfaIdx);
    String send(List<InspectPurchasingPerformanceVO> list);
}
