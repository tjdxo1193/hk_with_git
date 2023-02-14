package lims.api.integration.vo.rfc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RFCAssetsDepreciationVO {
    private String OUT_COMP_NO;
    private String ASSET_NO;
    private String EQCTG_TYPE;
    private String BUYER_AMT;
    private String DEP_AMT;
}