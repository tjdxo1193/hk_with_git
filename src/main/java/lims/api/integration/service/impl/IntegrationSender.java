package lims.api.integration.service.impl;

import lims.api.common.service.impl.ReportDesignerHelper;
import lims.api.integration.domain.eai.TrsResult;
import lims.api.integration.enums.ReportDivOfNonCfm;
import lims.api.integration.service.*;
import lims.api.integration.vo.ELNSendVO;
import lims.api.integration.vo.SAPSendVO;
import lims.api.integration.vo.intergation.ConvertMrd;
import lims.api.integration.vo.intergation.InterfaceSendVO;
import lims.api.integration.vo.intergation.TempFile;
import lims.api.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IntegrationSender {

    private final SAPService sapService;
    private final SRMService srmService;
    private final MESService mesService;
    private final QMSService qmsService;
    private final ELNService elnService;

    private final ReportDesignerHelper rdHelper;

    /**
     * 품질 시험의 특정 상태마다 연계 시스템으로 현재 시험 상태 정보를 보낸다.
     */
    public void sendTestStatus(InterfaceSendVO.TestStatus data) {
        sapService.publishTestStatus(data.toSAP());
        if (data.isMES()) {
            mesService.publishTestStatus(data.toMES());
        }
        if (data.isSRM()) {
            srmService.publishTestStatus(data.toSRM());
        }
    }

    /*
     * 품질 시험의 결과 판정 정보를 보낸다.
     */
    public void sendTestResult(InterfaceSendVO.TestResult data) {
        sapService.publishTestResultJudgment(data.toSAP());
        if (data.isMES()) {
            mesService.publishTestResultJudgment(data.toMES());
        }
        if (data.isSRM()) {
            srmService.publishTestResultJudgment(data.toSRM());
        }
    }

    /**
     * 부적합 보고서
     * SRM. MES -> 부적합 보고서(PDF), 재발방지대책서 (DOC) 전송
     */
    public void sendNonconformityReport(InterfaceSendVO.NonconformityReport data, ConvertMrd nonconformityReport, ConvertMrd reoccurReport) {
        if (nonconformityReport != null) {
            sendNonconformityReportToSRMAndMES(data, nonconformityReport, ReportDivOfNonCfm.A);
        }
        if (reoccurReport != null) {
            sendNonconformityReportToSRMAndMES(data, reoccurReport, ReportDivOfNonCfm.B);
        }
    }

    private void sendNonconformityReportToSRMAndMES(InterfaceSendVO.NonconformityReport data, ConvertMrd convertMrd, ReportDivOfNonCfm reportDiv) {
        TempFile tempFile = rdHelper.toTempFile(convertMrd.getName(), convertMrd.getParameter(), convertMrd.getTargetFullName());
        if (data.isMES()) {
            mesService.publishNonCfmReport(data.toMES(), FileUtil.getName(tempFile.getFile()), FileUtil.toBytes(tempFile.getFile()));
        }
        if (data.isSRM()) {
            srmService.publishNonCfmReport(data.toSRM(reportDiv), FileUtil.getName(tempFile.getFile()), FileUtil.toBytes(tempFile.getFile()));
        }
        tempFile.delete();
    }

    /**
     * ~~ 저장 시 시험항목 별 시험방법
     */
    public void sendMethodByItem(List<InterfaceSendVO.MethodByItem> data) {
        List<ELNSendVO.TestMethodByItem> param = new ArrayList<>();
        for (InterfaceSendVO.MethodByItem item : data) {
            ELNSendVO.TestMethodByItem el = ELNSendVO.TestMethodByItem.builder()
                    .amitmCd(item.getAmitmCd())
                    .cmdType(item.getCmdType())
                    .build();
            param.add(el);
        }
        elnService.publishTestMethodByItem(param);
    }

    /**
     * 1달 마다 구매오더 기준 품질 검사 횟수(실적)를 전송한다.
     */
    public TrsResult sendTestPerformanceOfPurchaseInbound(InterfaceSendVO.PurchaseInboundTestPerformance data) {
        return sapService.publishTestPerformanceOfPurchaseInbound(data.toSAP());
    }

    /**
     * 매일 생산입고 기준 품질 검사 횟수(실적)를 전송한다.
     */
    public TrsResult sendPerformanceOfManufactureInbound(InterfaceSendVO.ManufactureInboundTestPerformance data) {
        return sapService.publishTestPerformanceOfManufactureInbound(data.toSAP());
    }

    /**
     * 자산의 위치 변경 시 그 내역을 전송한다.
     */
    public void sendAssetsMovementHistory(List<SAPSendVO.AssetsMovementHistory> data) {
        sapService.publishAssetsMovementHistory(data);
    }

    /**
     * 승인 완료된 실험건을 QMS로 전송
     * - Order No, Lot No에 포함되는 모든 완제품들이 오더 마감 되어야 전송됨.
     */
    private void sendFinishedTestForShipt(String batchNo) {
        qmsService.publishShiptData(batchNo);
    }

    private void sendDeviation() {
        // TODO 일탈 정보를 QMS로 전송
    }


}