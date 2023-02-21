package lims.api.integration.vo;

import lims.api.integration.domain.eai.RevStateful;
import lims.api.util.process.KeyGenerator;
import lims.api.util.process.MappingKey;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class MESPackageSpecReportVO extends RevStateful implements KeyGenerator {

    @MappingKey
    private String matnr;
    @MappingKey
    private String version;
    private String fileName;
    private byte[] fileData;

}