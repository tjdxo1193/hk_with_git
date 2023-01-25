package lims.api.integration.model;

import lims.api.integration.vo.SAPTestRequestVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SAPRequestForTestRequest extends InterfaceInfo {

    private SAPTestRequestVO.RequestHeader requestHeader;
    private List<SAPTestRequestVO.RequestDetails> requestDetails;

}