package lims.api.re.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.re.dao.ReagentUsageHisDao;
import lims.api.re.enums.ReagentMaterialProcess;
import lims.api.re.service.ReagentUsageHisService;
import lims.api.re.vo.ReagentMaterialVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReagentUsageHisServiceImpl implements ReagentUsageHisService {

    private final ReagentUsageHisDao dao;

    @Override
    public List<ReagentMaterialVO> findAll(ReagentMaterialVO param) {
        List processCodeList = new ArrayList();
        param.setEtrProcCd(ReagentMaterialProcess.APPROVE_COMPLETE.getProcessCode());

        if(StringUtils.isEmpty(param.getMngProcCd())) {
            processCodeList.add(ReagentMaterialProcess.OPEN.getProcessCode());
        }else {
            processCodeList.add(ReagentMaterialProcess.OPEN.getProcessCode());
            processCodeList.add(ReagentMaterialProcess.DISPOSAL.getProcessCode());
        }
        param.setProcessCodeList(processCodeList);

        return dao.findAll(param);
    }

    @Override
    public ReagentMaterialVO create(ReagentMaterialVO param) {
        int result = dao.create(param);

        if(result == 0) {
            throw new NoCreatedDataException();
        }

        param.setLeftOverStok(calculateLeftOverStockWhenCreated(param));
        return param;
    }

    @Override
    public ReagentMaterialVO update(ReagentMaterialVO param) {
        int result = dao.update(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }

        param.setLeftOverStok(calculateLeftOverStockWhenUpdated(param));
        return param;
    }

    @Override
    public void delete(ReagentMaterialVO param) {
        int result = dao.delete(param);

        if(result == 0) {
            throw new NoDeletedDataException();
        }
    }

    /**
     * leftOverStok = 재고량 - 사용량
     */
    private float calculateLeftOverStockWhenCreated(ReagentMaterialVO param) {
        BigDecimal leftOverStok = new BigDecimal(Float.toString(param.getLeftOverStok()));
        BigDecimal useQty = new BigDecimal(Float.toString(param.getUseQty()));
        BigDecimal returnLeftOverStok = leftOverStok.subtract(useQty);
        return returnLeftOverStok.floatValue();
    }

    /**
     * leftOverStok = 기존사용량 + 재고량 - 사용량
     */
    private float calculateLeftOverStockWhenUpdated(ReagentMaterialVO param) {
        BigDecimal compareUseQty = new BigDecimal(Float.toString(param.getCompareUseQty()));
        BigDecimal leftOverStok = new BigDecimal(Float.toString(param.getLeftOverStok()));
        BigDecimal useQty = new BigDecimal(Float.toString(param.getUseQty()));
        BigDecimal returnLeftOverStok = compareUseQty.add(leftOverStok).subtract(useQty);
        return returnLeftOverStok.floatValue();
    }
}
