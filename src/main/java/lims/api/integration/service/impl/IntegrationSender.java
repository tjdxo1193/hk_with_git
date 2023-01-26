package lims.api.integration.service.impl;

import lims.api.common.service.impl.ReportDesignerHelper;
import lims.api.integration.service.MESService;
import lims.api.integration.service.QMSService;
import lims.api.integration.service.SAPService;
import lims.api.integration.service.SRMService;
import lims.api.integration.vo.intergation.ConvertMrd;
import lims.api.integration.vo.intergation.InterfaceSendVO;
import lims.api.integration.vo.intergation.TempFile;
import lims.api.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class IntegrationSender {

    private final SAPService sapService;
    private final SRMService srmService;
    private final MESService mesService;
    private final QMSService qmsService;

    private final ReportDesignerHelper rdHelper;

    /**
     * 품질 시험의 특정 상태마다 연계 시스템으로 현재 시험 상태 정보를 보낸다.
     */
    public void sendTestStatus(InterfaceSendVO.TestStatus data) {
        sapService.publishTestStatus(data.toSAP());
        srmService.publishTestStatus(data.toSRM());
        mesService.publishTestStatus(data.toMES());
    }

    /*
     * 품질 시험의 결과 판정 정보를 보낸다.
     */
    public void sendTestResult(InterfaceSendVO.TestResult data) {
        sapService.publishTestResultJudgment(data.toSAP());
        srmService.publishTestResultJudgment(data.toSRM());
        mesService.publishTestResultJudgment(data.toMES());
    }

    /**
     * 부적합 보고서
     * SRM. MES -> 부적합 보고서(PDF), 재발방지대책서 (DOC) 전송
     */
    public void sendNonconformityReport(InterfaceSendVO.NonconformityReport data, ConvertMrd nonconformityReport, ConvertMrd reoccurReport) {
        if (nonconformityReport != null) {
            sendNonconformityReportToSRMAndMES(data, nonconformityReport);
        }
        if (reoccurReport != null) {
            sendNonconformityReportToSRMAndMES(data, reoccurReport);
        }
    }

    private void sendNonconformityReportToSRMAndMES(InterfaceSendVO.NonconformityReport data, ConvertMrd convertMrd) {
        TempFile tempFile = toTempFile(convertMrd);
        srmService.publishNonCfmReport(data.toSRM(), FileUtil.getName(tempFile.getFile()), FileUtil.toBytes(tempFile.getFile()));
        mesService.publishNonCfmReport(data.toMES(), FileUtil.getName(tempFile.getFile()), FileUtil.toBytes(tempFile.getFile()));
        tempFile.delete();
    }

    private TempFile toTempFile(ConvertMrd mrdVO) {
        return rdHelper.toTempFile(mrdVO.getName(), mrdVO.getParameter(), mrdVO.getTargetFullName());
    }

    private void sendFinishedTestForShipt() {
        // TODO 승인 완료된 실험건들 QMS로 전송
    }

    private void sendDeviation() {
        // TODO 일탈 정보를 QMS로 전송
    }

    /**
     * 1달 마다 구매오더 기준 품질 검사 횟수를 전송한다.
     */
//    public void sendTestPerformanceOfPurchaseInbound(List<SAPSendVO.TestPerformanceOfPurchaseInbound> data) {
//        sapService.publishTestPerformanceOfPurchaseInbound(data);
//    }

    /**
     * 매일 생산입고 기준 품질 검사 횟수를 전송한다.
     */
//    public void sendPerformanceOfManufactureInbound(List<SAPSendVO.TestPerformanceOfManufactureInbound> data) {
//        sapService.publishTestPerformanceOfManufactureInbound(data);
//    }

    /**
     * 자산의 위치 변경 시 그 내역을 전송한다.
     */
//    public void sendAssetsMovementHistory(List<SAPSendVO.AssetsMovementHistory> data) {
//        sapService.publishAssetsMovementHistory(data);
//    }

    /**
     * 부적합 보고서
     */

    /**
     * 재발방지대책서
     */




}