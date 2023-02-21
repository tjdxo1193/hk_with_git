package lims.api.integration.vo;

import lims.api.util.process.KeyGenerator;
import lims.api.util.process.MappingKey;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SAPBusinessPartnerVO implements KeyGenerator {
    @MappingKey
    private String companyCode;
    @MappingKey
    private String partnerNo;
    private String partnerGroup;
    private String nameOrg1;
    private String nameOrg2;
    private String companyNumber;
    @Setter
    private Integer ifInfoIdx;

}