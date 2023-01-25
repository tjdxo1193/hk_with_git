package lims.api.integration.vo;

import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.enums.ReportDivOfConsignSupport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SRMSupplierReportVO extends RevStateful {

    private String ifId;
    private String phsOrderNo;
    private String lotNo;
    private String batchNo;
    private ReportDivOfConsignSupport reportDiv;
    private String fileId;
    private String fileName;
    private byte[] fileData;

    private Integer seq;

}