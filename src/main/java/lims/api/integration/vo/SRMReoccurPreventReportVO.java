package lims.api.integration.vo;

import lims.api.integration.domain.eai.RevStateful;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SRMReoccurPreventReportVO extends RevStateful {

    private String ifId;
    private String phsOrderNo;
    private Integer seq;
    private String lotNo;
    private String batchNo;
    private String fileId;
    private String fileName;
    private byte[] fileData;

}