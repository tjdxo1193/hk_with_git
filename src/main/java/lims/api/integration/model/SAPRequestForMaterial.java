package lims.api.integration.model;

import lims.api.integration.vo.SAPMaterialVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SAPRequestForMaterial extends InterfaceInfo {

    private List<SAPMaterialVO.Mara> mara;
    private List<SAPMaterialVO.Marc> marc;
    private List<SAPMaterialVO.Mvke> mvke;
    private List<SAPMaterialVO.Zmdv> zmdv;
    private List<SAPMaterialVO.Makt> makt;

}