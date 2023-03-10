package lims.api.common.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RFCAssetDepreciationRequestVO {
    @NotBlank
    private String anlkl;

    @NotBlank
    private String bzdat;
}