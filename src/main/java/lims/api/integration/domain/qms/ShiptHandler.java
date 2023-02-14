package lims.api.integration.domain.qms;

import lims.api.integration.vo.QMSSendVO;

import java.util.List;

public interface ShiptHandler {

    void runValidation();

    void fetch();

    boolean isReady();

    List<QMSSendVO.ShiptReq> getData();

}