package lims.api.common.vo;

import lims.api.common.enums.UseType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ESignVO {

    private UseType sign = UseType.N;
    private String reason;

    public void sign() {
        this.sign = UseType.Y;
    }

}