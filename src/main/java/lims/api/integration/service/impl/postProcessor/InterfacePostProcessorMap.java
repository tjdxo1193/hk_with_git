package lims.api.integration.service.impl.postProcessor;

import lims.api.integration.enums.RevInterface;
import lims.api.integration.service.impl.postProcessor.eln.ELNCtReportPostProcessor;
import lims.api.integration.service.impl.postProcessor.eln.ELNStandardSpecPostProcessor;
import lims.api.integration.service.impl.postProcessor.mes.MESFinalOrderPostProcessor;
import lims.api.integration.service.impl.postProcessor.mes.MESPackageSpecReportPostProcessor;
import lims.api.integration.service.impl.postProcessor.sap.*;
import lims.api.integration.service.impl.postProcessor.srm.SRMConsignAndSupplierReportPostProcessor;
import lims.api.integration.service.impl.postProcessor.srm.SRMFinalOrderPostProcessor;
import lims.api.integration.service.impl.postProcessor.srm.SRMReoccurPreventReportPostProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class InterfacePostProcessorMap {

    private final Map<RevInterface, PostProcessor> map = new HashMap<>();

    private final SAPMaterialPostProcessor sapMaterialPostProcessor;
    private final SAPTestRequestPostProcessor sapTestRequestPostProcessor;
    private final SAPBOMPostProcessor sapBomPostProcessor;
    private final SAPCharacteristicPostProcessor sapCharacteristicPostProcessor;
    private final SAPInputPerformancePostProcessor sapInputPerformancePostProcessor;

    private final ELNCtReportPostProcessor elnCtReportPostProcessor;
    private final ELNStandardSpecPostProcessor elnStandardSpecPostProcessor;

    private final SRMFinalOrderPostProcessor srmFinalOrderPostProcessor;
    private final SRMReoccurPreventReportPostProcessor srmReoccurPreventReportPostProcessor;
    private final SRMConsignAndSupplierReportPostProcessor srmConsignAndSupplierReportPostProcessor;

    private final MESFinalOrderPostProcessor mesFinalOrderPostProcessor;
    private final MESPackageSpecReportPostProcessor mesPackageSpecReportPostProcessor;

    @PostConstruct
    public void init() {
        map.put(RevInterface.SAP_MATERIAL, sapMaterialPostProcessor);
        map.put(RevInterface.SAP_TEST_REQUEST, sapTestRequestPostProcessor);
        map.put(RevInterface.SAP_BOM, sapBomPostProcessor);
        map.put(RevInterface.SAP_CHARACTERISTIC, sapCharacteristicPostProcessor);
        map.put(RevInterface.SAP_INPUT_PERFORMANCE, sapInputPerformancePostProcessor);

        map.put(RevInterface.ELN_CT_REPORT, elnCtReportPostProcessor);
        map.put(RevInterface.ELN_STANDARD_FINISH_AND_SEMI, elnStandardSpecPostProcessor);

        map.put(RevInterface.SRM_FINAL_ORDER, srmFinalOrderPostProcessor);
        map.put(RevInterface.SRM_REOCCUR_PREVENT_REPORT, srmReoccurPreventReportPostProcessor);
        map.put(RevInterface.SRM_CONSIGNMENT_AND_SUPPLIER_REPORT, srmConsignAndSupplierReportPostProcessor);

        map.put(RevInterface.MES_FINAL_ORDER, mesFinalOrderPostProcessor);
        map.put(RevInterface.MES_PACKAGE_SPEC_REPORT, mesPackageSpecReportPostProcessor);

    }

    public PostProcessor get(RevInterface processor) {
        if (!map.containsKey(processor)) {
            throw new NoSuchElementException("no exists post processor of " + processor.getId());
        }
        return map.get(processor);
    }

}