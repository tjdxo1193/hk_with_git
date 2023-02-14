package lims.api.sc.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.integration.domain.eai.TrsResult;
import lims.api.integration.service.impl.IntegrationSender;
import lims.api.integration.vo.intergation.InterfaceSendVO;
import lims.api.sc.dao.InspectPurchasingPerformanceDao;
import lims.api.sc.enums.InterfaceSucceedDiv;
import lims.api.sc.enums.InterfaceTransferDiv;
import lims.api.sc.service.InspectPurchasingPerformanceService;
import lims.api.sc.vo.InspectProductionPerformanceVO;
import lims.api.sc.vo.InspectPurchasingPerformanceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InspectPurchasingPerformanceServiceImpl implements InspectPurchasingPerformanceService {

    private final InspectPurchasingPerformanceDao dao;
    private final IntegrationSender sender;

    @Override
    public List<InspectPurchasingPerformanceVO> find(InspectPurchasingPerformanceVO param) {
        return dao.find(param);
    }

    @Override
    public List<InspectPurchasingPerformanceVO> findDetail(String plntCd, String mtrCd, String phsOrderNo, String etrDt) {
        return dao.findDetail(plntCd, mtrCd, phsOrderNo, etrDt);
    }

    @Override
    public List<InspectPurchasingPerformanceVO> getRecord(Integer ispPhsPfaIdx) {
        return dao.getRecord(ispPhsPfaIdx);
    }

    @Override
    public String send(List<InspectPurchasingPerformanceVO> list) {
        String ifStata = null;
        for (InspectPurchasingPerformanceVO param : list) {
            TrsResult trsResult = sendIF(param);

            param = setParameter(trsResult, param);
            ifStata = param.getIfStt();

            int result = dao.insertPhsRecord(param);
            if(result == 0) {
                throw new NoCreatedDataException();
            }

            result += dao.updatePhsIdx(param);
            if(result == 1) {
                throw new NoUpdatedDataException();
            }
        }

        return ifStata;
    }

    private TrsResult sendIF(InspectPurchasingPerformanceVO param) {
        InterfaceSendVO.PurchaseInboundTestPerformance data = InterfaceSendVO.PurchaseInboundTestPerformance.builder()
                .year(param.getFiscalYr())
                .month(param.getMonth())
                .plntCd(param.getPlntCd())
                .pitmCd(param.getMtrCd())
                .phsOrderNo(param.getPhsOrderNo())
                .inputQty(param.getEtrQty())
                .testCnt(param.getIspScr())
                .build();
        return sender.sendTestPerformanceOfPurchaseInbound(data);
    }

    private InspectPurchasingPerformanceVO setParameter(TrsResult trsResult, InspectPurchasingPerformanceVO param) {
        if(trsResult.isSuccess()) {
            param.setIfStt(InterfaceSucceedDiv.SUCCEED.getType());
        }else {
            param.setIfStt(InterfaceSucceedDiv.FAIL.getType());
        }

        param.setDegree(trsResult.getDegree());
        param.setIfInfoIdx(trsResult.getIfInfoIdx());
        param.setIfDs(Timestamp.valueOf(trsResult.getTrsDate()));
        param.setIfMtdDiv(InterfaceTransferDiv.MANUAL.getTransferDiv());

        if(param.getIspPhsPfaIdx() == null) {
            int ispPhsPfaIdx = dao.findIspPhsPfaIdx();
            param.setIspPhsPfaIdx(ispPhsPfaIdx);
        }

        return param;
    }
}
