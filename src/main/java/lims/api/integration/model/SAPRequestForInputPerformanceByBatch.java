package lims.api.integration.model;

import lims.api.integration.vo.SAPInputPerformanceByBatchVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SAPRequestForInputPerformanceByBatch extends InterfaceInfo {

    private List<SAPInputPerformanceByBatchVO.InputPerformanceHeader> performanceHeader;
    private List<SAPInputPerformanceByBatchVO.InputPerformanceDetail> performanceDetails;

}