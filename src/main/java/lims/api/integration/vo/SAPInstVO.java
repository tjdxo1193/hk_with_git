package lims.api.integration.vo;

import lims.api.integration.model.SAPRequestForInst;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SAPInstVO {

    public static SAPInstVO of(SAPRequestForInst vo) {
        return SAPInstVO.builder().build();
    }

}