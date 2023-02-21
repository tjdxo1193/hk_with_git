package lims.api.integration.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.enums.FinalOrderStatus;
import lims.api.util.process.KeyGenerator;
import lims.api.util.process.MappingKey;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class MESFinalOrderVO extends RevStateful implements KeyGenerator {
    @MappingKey
    private String pdtOrderNo;
    @MappingKey
    private String lotNo;
    private String orderItm;
    @EnumType(FinalOrderStatus.class)
    private int status;
}