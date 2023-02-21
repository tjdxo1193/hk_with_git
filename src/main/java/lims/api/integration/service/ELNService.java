package lims.api.integration.service;

import lims.api.integration.enums.ELNCmdType;
import lims.api.integration.vo.ELNCtReportVO;
import lims.api.integration.vo.ELNStandardSpecVO;
import lims.api.integration.vo.ELNSendVO;

import java.util.List;

public interface ELNService {

    void saveCtReport(Integer infoIdx, ELNCtReportVO data);

    void saveFinishedAndSemiStandard(Integer infoIdx, List<ELNStandardSpecVO> data);

    void publishTestMethodByItem(List<ELNSendVO.TestMethodByItem> ids);

}