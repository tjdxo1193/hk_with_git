package lims.api.integration.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.util.process.KeyGenerator;
import lims.api.util.process.MappingKey;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.enums.FinalOrderStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class SRMFinalOrderVO extends RevStateful implements KeyGenerator {
    @MappingKey
    private String phsOrderNo;
    @MappingKey
    private String lotNo;
    @EnumType(FinalOrderStatus.class)
    private String status;
    private String orderItm;
    private String ifId;
}