package lims.api.ms.enums;

import java.util.Set;

public enum PItemType {
    FINISHED_SET("S0180100"),
    FINISHED_SINGLE("S0180101"),
    FINISHED_BEAUTIFUL_PACKAGING("S0180102"),
    SEMI_MANUFACTURES_FILLING_FOAM("S0180201"),
    SEMI_MANUFACTURES_OTHER_PRODUCT("S0180202"),
    SEMI_MANUFACTURES_BULK("S0180203"),
    SEMI_MANUFACTURES_BASE("S0180204"),
    RAW_MATERIAL("S0180400"),
    PACKAGING_MATERIAL("S0180500"),
    GOODS("S0180600");

    private final String code;

    PItemType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Set<String> getCodesRelatedToSpec() {
        return Set.of(
                SEMI_MANUFACTURES_BASE.getCode(),
                SEMI_MANUFACTURES_BULK.getCode(),
                SEMI_MANUFACTURES_FILLING_FOAM.getCode(),
                SEMI_MANUFACTURES_OTHER_PRODUCT.getCode(),
                RAW_MATERIAL.getCode(),
                GOODS.getCode()
        );
    }

}