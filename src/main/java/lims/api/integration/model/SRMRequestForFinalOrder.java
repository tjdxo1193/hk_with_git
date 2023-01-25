package lims.api.integration.model;

import lims.api.integration.vo.SRMFinalOrderVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SRMRequestForFinalOrder extends InterfaceInfo {

    List<SRMFinalOrderVO> dataList;

}