package lims.api.integration.domain.qms;

import lims.api.integration.vo.QMSSendVO;

import java.util.List;

public interface ShiptRepository {

    List<QMSSendVO.ShiptReq> getShiptRequests();

    List<QMSSendVO.ShiptTest> findTestAllByOrderNoAndLotNo(QMSSendVO.ShiptReq req);

    List<QMSSendVO.ShiptPerform> findShiptPerformByKey(QMSSendVO.ShiptTest test);

    List<QMSSendVO.ShiptValidate> findFinalOrdersByOrderNoAndLotNo(QMSSendVO.ShiptReq req);

}