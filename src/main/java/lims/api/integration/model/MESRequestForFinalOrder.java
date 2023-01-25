package lims.api.integration.model;

import lims.api.integration.vo.MESFinalOrderVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MESRequestForFinalOrder extends InterfaceInfo {
    List<MESFinalOrderVO> dataList;
}