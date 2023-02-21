package lims.api.integration.domain.qms;

import lims.api.integration.dao.QMSDao;
import lims.api.integration.vo.QMSSendVO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class MESShiptRepository implements ShiptRepository {

    private final QMSDao qmsDao;
    private final String batchNo;

    public MESShiptRepository(QMSDao qmsDao, String batchNo) {
        this.qmsDao = qmsDao;
        this.batchNo = batchNo;
    }

    @Override
    public List<QMSSendVO.ShiptReq> getShiptRequests() {
        return qmsDao.findShiptReqByBatchNo(batchNo)
                .stream()
                .filter(base -> StringUtils.isNotEmpty(base.getPdtOrderNo()))
                .collect(Collectors.toList());
    }

    @Override
    public List<QMSSendVO.ShiptTest> findTestAllByOrderNoAndLotNo(QMSSendVO.ShiptReq req) {
        return qmsDao.findMESTestAllByOrderNoAndLotNo(req);
    }

    @Override
    public List<QMSSendVO.ShiptPerform> findShiptPerformByKey(QMSSendVO.ShiptTest test) {
        return qmsDao.findMESShiptPerformByKey(test);
    }

    @Override
    public List<QMSSendVO.ShiptValidate> findFinalOrdersByOrderNoAndLotNo(QMSSendVO.ShiptReq req) {
        return qmsDao.findMESFinalOrdersByOrderNoAndLotNo(req);
    }
}