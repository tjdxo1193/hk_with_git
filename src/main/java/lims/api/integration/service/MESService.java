package lims.api.integration.service;

import lims.api.integration.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MESService {

    void savePackageSpecReport(Integer infoIdx, MESPackageSpecReportVO data, List<MultipartFile> files);

    void saveFinalOrder(Integer infoIdx, List<MESFinalOrderVO> data);

    void publishTestResultJudgment(MESSendVO.TestResult data);

    void publishTestStatus(MESSendVO.TestStatus data);

    void publishNonCfmReport(MESSendVO.NonCfmReport data, String fileName, byte[] fileBytes);

}