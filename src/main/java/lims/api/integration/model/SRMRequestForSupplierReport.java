package lims.api.integration.model;

import lims.api.integration.vo.SRMSupplierReportVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SRMRequestForSupplierReport extends InterfaceInfo {

    SRMSupplierReportVO dataList;

}