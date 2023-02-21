package lims.api.schedule.vo;

import lims.api.integration.annotation.EnumType;
import lims.api.sc.enums.InterfaceSucceedDiv;
import lims.api.sc.enums.InterfaceTransferDiv;
import lims.api.util.process.KeyGenerator;
import lims.api.util.process.MappingKey;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class InputPerformVO {

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class Manufacture implements KeyGenerator {
        @MappingKey
        private Integer ispPdtPfaIdx;
        @MappingKey
        private Integer srlNo;
        private String ispPfaCanlYn;

        @EnumType(InterfaceTransferDiv.class)
        private String ifMtdDiv;
        @EnumType(InterfaceSucceedDiv.class)
        private String ifStt;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class Purchase implements KeyGenerator {
        @MappingKey
        private Integer ispPhsPfaIdx;
        @MappingKey
        private Integer srlNo;

        @EnumType(InterfaceTransferDiv.class)
        private String ifMtdDiv;
        @EnumType(InterfaceSucceedDiv.class)
        private String ifStt;
    }

}