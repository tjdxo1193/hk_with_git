package lims.api.integration.model;

import lims.api.integration.vo.SAPBomVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SAPRequestForBOM extends InterfaceInfo {

    private String guid;

    private List<SAPBomVO> dataList;

}