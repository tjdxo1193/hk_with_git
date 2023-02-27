package lims.api.integration.service;

import lims.api.integration.vo.QMSSendVO;
import lims.api.integration.vo.SAPMaterialVO;

import java.util.List;

public interface QMSService {

    void publishMaterial(SAPMaterialVO param);

    void publishShiptData(String batchNo);

    void publishShiptData(List<QMSSendVO.ShiptReq> data);

}