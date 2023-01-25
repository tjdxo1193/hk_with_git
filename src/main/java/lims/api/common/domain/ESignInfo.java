package lims.api.common.domain;

import lims.api.common.enums.UseType;
import lims.api.common.vo.ESignVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ESignInfo {

    private UseType sign;
    private String reason;

    public boolean isSigned() {
        return sign == UseType.Y;
    }

    public boolean isUnsigned() {
        return sign == UseType.N;
    }

    public static ESignInfo of(ESignVO vo) {
        return ESignInfo.builder()
                .sign(vo.getSign())
                .reason(vo.getReason())
                .build();
    }


}