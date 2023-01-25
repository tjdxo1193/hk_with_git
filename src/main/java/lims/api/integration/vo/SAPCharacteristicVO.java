package lims.api.integration.vo;

import lims.api.util.process.KeyGenerator;
import lims.api.util.process.MappingKey;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.model.SAPRequestForCharacteristic;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SAPCharacteristicVO {

    private List<Cabn> cabn;
    private List<Ksml> ksml;

    public static SAPCharacteristicVO of(SAPRequestForCharacteristic vo) {
        return new ModelMapper().map(vo, SAPCharacteristicVO.class);
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public static class Cabn extends RevStateful implements KeyGenerator {
        @MappingKey
        private String charCode;
        private String charCodeDesc;
        private String charDataType;
        @MappingKey
        private String charValChar;
        @MappingKey
        private String charValNum;
        private String charValDesc;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public static class Ksml extends RevStateful implements KeyGenerator {
        @MappingKey
        private String classType;
        @MappingKey
        private String clazz;
        private String className;
        @MappingKey
        private String charCode;
    }

}