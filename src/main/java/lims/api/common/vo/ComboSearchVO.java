package lims.api.common.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ComboSearchVO {
    private String plantCode;
    private String parentCode;
    private String code;
    private Integer level;

    public static ComboSearchVO ofParentCodeByUserCode(String plantCode, String parentCode) {
        return ComboSearchVO.builder()
                .plantCode(plantCode)
                .parentCode(parentCode)
                .build();
    }

    public static ComboSearchVO ofParentCodeBySystemCode(String parentCode) {
        return ComboSearchVO.builder()
                .parentCode(parentCode)
                .build();
    }

    public static ComboSearchVO ofCode(String plantCode, String code) {
        return ComboSearchVO.builder()
                .plantCode(plantCode)
                .code(code)
                .build();
    }

    public static ComboSearchVO ofLevel(String plantCode, Integer level) {
        return ComboSearchVO.builder()
                .plantCode(plantCode)
                .level(level)
                .build();
    }
}