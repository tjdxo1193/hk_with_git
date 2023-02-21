package lims.api.integration.model;

import lims.api.integration.vo.MESPackageSpecReportVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MESRequestForPackageSpecReport extends InterfaceInfo {

    MESPackageSpecReportVO dataList;

}