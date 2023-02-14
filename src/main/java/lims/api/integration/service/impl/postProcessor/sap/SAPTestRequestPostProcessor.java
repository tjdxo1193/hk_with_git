package lims.api.integration.service.impl.postProcessor.sap;

import lims.api.common.enums.PlantType;
import lims.api.common.enums.UseType;
import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.annotation.PostProcessorRevExceptionHandler;
import lims.api.integration.dao.SAPDao;
import lims.api.integration.dao.SAPPostProcessDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.service.impl.postProcessor.PostProcessor;
import lims.api.integration.vo.SAPPostProcessVO;
import lims.api.integration.vo.SAPTestRequestVO;
import lims.api.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SAPTestRequestPostProcessor implements PostProcessor {

    private final SAPDao sapDao;
    private final SAPPostProcessDao postProcessDao;

    @Override
    @PostProcessorRevExceptionHandler
    public synchronized void execute(RevStateful rev) {
        int degree = rev.getDegree();

        SAPTestRequestVO.RequestHeader header = sapDao.findOneTestReqByDegree(degree);

        if (isCancel(header.getZexfield2())) {
            cancelPItemProcess(header);
        } else {
            createPItemRequest(header, degree);
        }
    }

    private boolean isCancel(String nullOrX) {
        return "X".equals(nullOrX);
    }

    private void cancelPItemProcess(SAPTestRequestVO.RequestHeader header) {
        int count = 0;

        count += cancelTestRequest(header);
        count += cancelTestRequestProcess(header);

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }
    }

    private int cancelTestRequest(SAPTestRequestVO.RequestHeader header) {
        SAPPostProcessVO.TestRequest.PItemReq pItemReq = convertTo(header);
        return postProcessDao.cancelTestRequestToCancel(pItemReq);
    }

    private int cancelTestRequestProcess(SAPTestRequestVO.RequestHeader header) {
        List<Integer> cancelReqIdxes = postProcessDao.findReqIdxByIspReqNo(header.getZqcreqno());

        if (CollectionUtils.isEmpty(cancelReqIdxes)) {
            return 0;
        }

        String cancelIdxesInClause = cancelReqIdxes.stream().map(String::valueOf).collect(Collectors.joining(","));
        List<SAPPostProcessVO.TestRequest.PItemReqProcess> notCanceledProcesses = postProcessDao.findNotCanceledTestReqProcessAllByReqIdx(cancelIdxesInClause);

        if (CollectionUtils.isEmpty(notCanceledProcesses)) {
            return 0;
        }

        int count = 0;
        for (SAPPostProcessVO.TestRequest.PItemReqProcess notCanceledProcess : notCanceledProcesses) {
            SAPPostProcessVO.TestRequest.PItemReqProcess holdProcess = SAPPostProcessVO.TestRequest.PItemReqProcess.builder()
                    .plntCd(header.getWerks())
                    .ansIdx(notCanceledProcess.getAnsIdx())
                    .hldYn(UseType.Y)
                    .hldUid(PlantType.SYSTEM.getUid())
                    .hldRea("SAP 입고 취소")
                    .build();
            count += postProcessDao.cancelTestRequestProcessToCancel(holdProcess);
        }
        return count;
    }

    private void createPItemRequest(SAPTestRequestVO.RequestHeader header, int degree) {
        int count = 0;
        String plantCode = header.getWerks();
        Integer reqIdx = postProcessDao.nextIdxInPItemReq();

        SAPPostProcessVO.TestRequest.PItemReq pItemReq = convertTo(header);
        pItemReq.setReqIdx(reqIdx);

        count += postProcessDao.createPItemRequest(pItemReq);
        count += createPItemRequestNonCfm(plantCode, reqIdx, degree);

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }
    }

    private int createPItemRequestNonCfm(String plantCode, Integer reqIdx, int degree) {
        int count = 0;
        List<SAPTestRequestVO.RequestDetails> details = sapDao.findAllTestReqDetailsByDegree(degree);

        if (CollectionUtils.isNotEmpty(details)) {
            for (SAPTestRequestVO.RequestDetails detail : details) {
                SAPPostProcessVO.TestRequest.PItemReqNonCfm param = SAPPostProcessVO.TestRequest.PItemReqNonCfm.builder()
                        .plntCd(plantCode)
                        .reqIdx(reqIdx)
                        .build();
                Integer seq = postProcessDao.nextSeqInPItemRequestNonCfm(param);

                SAPPostProcessVO.TestRequest.PItemReqNonCfm pItemReqNonCfm = SAPPostProcessVO.TestRequest.PItemReqNonCfm.builder()
                        .plntCd(plantCode)
                        .reqIdx(reqIdx)
                        .nonCfmSeq(seq)
                        .ispReqNo(detail.getZqcreqno())
                        .ispReqNoBlk(detail.getZqcnoBlk())
                        .zexfield1(detail.getZexfield1())
                        .zexfield2(detail.getZexfield2())
                        .zexfield3(detail.getZexfield3())
                        .zexfield4(detail.getZexfield4())
                        .zexfield5(detail.getZexfield5())
                        .ifInfoIdx(detail.getIfInfoIdx())
                        .build();
                count += postProcessDao.createPItemRequestNonCfm(pItemReqNonCfm);
            }
        }
        return count;
    }

    private SAPPostProcessVO.TestRequest.PItemReq convertTo(SAPTestRequestVO.RequestHeader header) {
        return SAPPostProcessVO.TestRequest.PItemReq.builder()
                .plntCd(header.getWerks())
                .ispReqNo(header.getZqcreqno())
                .mtrCd(header.getMatnr())
                .mtrNm(header.getMaktx())
                .batchNo(header.getCharg())
                .etrQty(header.getErfmg())
                .inpUnit(header.getErfme())
                .savePla(header.getLgort())
                .etrDt(StringUtil.Date.addHyphen(header.getBudat()))
                .phsOrderTyp(header.getBsart())
                .phsOrderNo(header.getEbeln())
                .phsOrderItm(header.getEbelp())
                .itmCtg(header.getPstyp())
                .phsUnitPre(header.getNetpr())
                .amtUnit(header.getPeinh())
                .currCd(header.getWaers())
                .amtLoccurr(header.getDmbtr())
                .phsOrderQty(header.getMenge())
                .phsOrderUnit(header.getMeins())
                .splCd(header.getLifnr())
                .splNm(header.getName1())
                .phsCrgNm(header.getAfnam())
                .dlvYn(header.getZparcel())
                .vdrCtrtDt(StringUtil.Date.addHyphen(header.getLfdat()))
                .vdrRsvTm(header.getZdeliverytime())
                .vdrRptRcpCrst(header.getZcoa())
                .lotNo(header.getZlotNo())
                .repBomNo(header.getZaltBomNo())
                .splLotNo(header.getZvndLotNo())
                .makDt(StringUtil.Date.addHyphen(header.getHsdat()))
                .strgLmt(header.getZstoringEndDate())
                .useLmt(header.getZshelfLifeEndDate())
                .pdtOrderTyp(header.getAuart())
                .pdtOrderNo(header.getAufnr())
                .itnPrsCompCd(header.getZsubconno())
                .itnPrsCompNm(header.getZsubconname())
                .makEqp(header.getZequnr())
                .wrkNm(header.getZworker())
                .mtrDocNo(header.getMblnr())
                .mtrDocYr(header.getMjahr())
                .mtrDocItm(header.getZeile())
                .csmBpCd(header.getZcustomer())
                .csmNm(header.getZcustomername())
                .addCol1(header.getZexfield1())
                .addCol2(header.getZexfield2())
                .addCol3(header.getZexfield3())
                .addCol4(header.getZexfield4())
                .addCol5(header.getZexfield5())
                .revDs(header.getCrtDs())
                .build();
    }
}