package lims.api.sd.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.sd.dao.StdItemUsageHisDao;
import lims.api.sd.enums.StandardMaterialProcess;
import lims.api.sd.service.StdItemUsageHisService;
import lims.api.sd.vo.StandardMaterialVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StdItemUsageHisServiceImpl implements StdItemUsageHisService {

    private final StdItemUsageHisDao dao;

    @Override
    public List<StandardMaterialVO> findAll(StandardMaterialVO param) {
        List processCodeList = new ArrayList();
        param.setEtrProcCd(StandardMaterialProcess.APPROVE_COMPLETE.getProcessCode());

        if(StringUtils.isEmpty(param.getMngProcCd())) {
            processCodeList.add(StandardMaterialProcess.OPEN.getProcessCode());
        }else {
            processCodeList.add(StandardMaterialProcess.OPEN.getProcessCode());
            processCodeList.add(StandardMaterialProcess.DISPOSAL.getProcessCode());
        }
        param.setProcessCodeList(processCodeList);

        return dao.findAll(param);
    }

    @Override
    public List<StandardMaterialVO> findOpenItem(StandardMaterialVO param) {
        param.setEtrProcCd(StandardMaterialProcess.APPROVE_COMPLETE.getProcessCode());
        param.setMngProcCd(StandardMaterialProcess.OPEN.getProcessCode());
        return dao.findOpenItem(param);
    }

    @Override
    public StandardMaterialVO create(StandardMaterialVO param) {
        int result = dao.create(param);

        if(result == 0) {
            throw new NoCreatedDataException();
        }

        param.setLeftOverStok(calculateLeftOverStockWhenCreated(param));
        return param;
    }

    @Override
    public StandardMaterialVO update(StandardMaterialVO param) {
        int result = dao.update(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }

        param.setLeftOverStok(calculateLeftOverStockWhenUpdated(param));
        return param;
    }

    @Override
    public void delete(StandardMaterialVO param) {
        int result = dao.delete(param);

        if(result == 0) {
            throw new NoDeletedDataException();
        }
    }

    /**
     * leftOverStok = 재고량 - 사용량
     */
    private float calculateLeftOverStockWhenCreated(StandardMaterialVO param) {
        BigDecimal leftOverStok = new BigDecimal(Float.toString(param.getLeftOverStok()));
        BigDecimal useQty = new BigDecimal(Float.toString(param.getUseQty()));
        BigDecimal returnLeftOverStok = leftOverStok.subtract(useQty);
        return returnLeftOverStok.floatValue();
    }

    /**
     * leftOverStok = 기존사용량 + 재고량 - 사용량
     */
    private float calculateLeftOverStockWhenUpdated(StandardMaterialVO param) {
        BigDecimal compareUseQty = new BigDecimal(Float.toString(param.getCompareUseQty()));
        BigDecimal leftOverStok = new BigDecimal(Float.toString(param.getLeftOverStok()));
        BigDecimal useQty = new BigDecimal(Float.toString(param.getUseQty()));
        BigDecimal returnLeftOverStok = compareUseQty.add(leftOverStok).subtract(useQty);
        return returnLeftOverStok.floatValue();
    }
}
