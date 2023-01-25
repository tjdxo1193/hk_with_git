package lims.api.integration.service;

import lims.api.integration.vo.IfInfoVO;

public interface InterfaceInfoService {

    Integer createInfo(IfInfoVO data);

    int updateStatusInfo(IfInfoVO data);

}