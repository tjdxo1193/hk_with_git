package lims.api.sc.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.integration.domain.eai.TrsResult;
import lims.api.integration.service.impl.IntegrationSender;
import lims.api.integration.vo.intergation.InterfaceSendVO;
import lims.api.sc.dao.InspectProductionPerformanceDao;
import lims.api.sc.enums.InterfaceSucceedDiv;
import lims.api.sc.enums.InterfaceTransferDiv;
import lims.api.sc.service.InspectProductionPerformanceService;
import lims.api.sc.vo.InspectProductionPerformanceVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InspectProductionPerformanceServiceImpl implements InspectProductionPerformanceService {

    private final InspectProductionPerformanceDao dao;
    private final IntegrationSender sender;

    @Override
    public List<InspectProductionPerformanceVO> find(InspectProductionPerformanceVO param) {
        return dao.find(param);
    }

    @Override
    public List<InspectProductionPerformanceVO> findDetail(String pitmCd, String pdtOrderNo, String batchNo, String ispReqDt) {
        return dao.findDetail(pitmCd, pdtOrderNo, batchNo, ispReqDt);
    }

    @Override
    public List<InspectProductionPerformanceVO> getRecord(Integer ispPdtPfaIdx) {
        return dao.getRecord(ispPdtPfaIdx);
    }

    @Override
    public String send(List<InspectProductionPerformanceVO> list) {
        String ifStata = null;
        for (InspectProductionPerformanceVO param : list) {
            TrsResult trsResult = sendIF(param);

            param = setParameter(trsResult, param);
            ifStata = param.getIfStt();

            int result = dao.insertPdtRecord(param);
            if(result == 0) {
                throw new NoCreatedDataException();
            }

            result += dao.updatePdtIdx(param);
            if(result == 1) {
                throw new NoUpdatedDataException();
            }
        }

        return ifStata;
    }

    private TrsResult sendIF(InspectProductionPerformanceVO param) {
        String replaceIspReqDt = param.getIspReqDt().replace("-", "");
        InterfaceSendVO.ManufactureInboundTestPerformance data = InterfaceSendVO.ManufactureInboundTestPerformance.builder()
                .pitmCd(param.getPitmCd())
                .pdtOrderNo(param.getPdtOrderNo())
                .batchNo(param.getBatchNo())
                .testReqDt(replaceIspReqDt)
                .testCnt(param.getIspScr())
                .build();
        return sender.sendPerformanceOfManufactureInbound(data);
    }

    private InspectProductionPerformanceVO setParameter(TrsResult trsResult, InspectProductionPerformanceVO param) {
        if(trsResult.isSuccess()) {
            param.setIfStt(InterfaceSucceedDiv.SUCCEED.getType());
        }else {
            param.setIfStt(InterfaceSucceedDiv.FAIL.getType());
        }

        param.setDegree(trsResult.getDegree());
        param.setIfInfoIdx(trsResult.getIfInfoIdx());
        param.setIfDs(Timestamp.valueOf(trsResult.getTrsDate()));
        param.setIfMtdDiv(InterfaceTransferDiv.MANUAL.getTransferDiv());

        if(param.getIspPdtPfaIdx() == null) {
            int ispPdtPfaIdx = dao.findIspPdtPfaIdx();
            param.setIspPdtPfaIdx(ispPdtPfaIdx);
        }

        return param;
    }
}