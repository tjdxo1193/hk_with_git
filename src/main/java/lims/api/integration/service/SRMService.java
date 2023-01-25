package lims.api.integration.service;

import lims.api.integration.vo.SRMFinalOrderVO;
import lims.api.integration.vo.SRMReoccurPreventReportVO;
import lims.api.integration.vo.SRMSendVO;
import lims.api.integration.vo.SRMSupplierReportVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SRMService {

    void saveReoccurPreventReport(Integer infoIdx, SRMReoccurPreventReportVO data, List<MultipartFile> files);

    void saveSupplierReport(Integer infoIdx, SRMSupplierReportVO data, List<MultipartFile> files);

    void saveFinalOrder(Integer infoIdx, List<SRMFinalOrderVO> data);

    void publishTestResultJudgment(SRMSendVO.TestResult data);

    void publishTestStatus(SRMSendVO.TestStatus data);

    void publishNonCfmReport(SRMSendVO.NonCfmReport data, String fileName, byte[] fileBytes);

}