package lims.api.integration.model;

import lims.api.integration.vo.SAPCharacteristicVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SAPRequestForCharacteristic extends InterfaceInfo {

    private List<SAPCharacteristicVO.Cabn> cabn;
    private List<SAPCharacteristicVO.Ksml> ksml;

}