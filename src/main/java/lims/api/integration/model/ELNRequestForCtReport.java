package lims.api.integration.model;

import lims.api.integration.vo.ELNCtReportVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ELNRequestForCtReport extends InterfaceInfo {

    private ELNCtReportVO.File file;
    private List<ELNCtReportVO.Matnr> matnr;

}