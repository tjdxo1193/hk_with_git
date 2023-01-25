package lims.api.integration.model;

import lims.api.integration.vo.ELNStandardSpecVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ELNRequestForStandardSpec extends InterfaceInfo {

    List<ELNStandardSpecVO> standard;

}