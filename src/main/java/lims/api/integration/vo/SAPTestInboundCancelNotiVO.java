package lims.api.integration.vo;

import lims.api.integration.model.SAPRequestForTestInboundCancelNoti;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SAPTestInboundCancelNotiVO {

    public static SAPTestInboundCancelNotiVO of(SAPRequestForTestInboundCancelNoti vo) {
        return SAPTestInboundCancelNotiVO.builder().build();
    }

}