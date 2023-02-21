package lims.api.integration.vo.rfc;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RFCBusinessPartnerVO {

    private String bukrs;
    private String partner;
    private String buGroup;
    private String nameOrg1;
    private String nameOrg2;
    private String taxnum2;

}