package lims.api.integration.enums;

import lims.api.ms.enums.PItemType;

import java.util.Arrays;

public enum MaterialMRP {
    FINISHED_SET("100", PItemType.FINISHED_SET),
    FINISHED_SINGLE("101", PItemType.FINISHED_SINGLE),
    FINISHED_BEAUTIFUL_PACKAGING("102", PItemType.FINISHED_BEAUTIFUL_PACKAGING),
    SEMI_MANUFACTURES_FILLING_FOAM("201", PItemType.SEMI_MANUFACTURES_FILLING_FOAM),
    SEMI_MANUFACTURES_OTHER_PRODUCT("202", PItemType.SEMI_MANUFACTURES_OTHER_PRODUCT),
    SEMI_MANUFACTURES_BULK("203", PItemType.SEMI_MANUFACTURES_BULK),
    SEMI_MANUFACTURES_BASE("204", PItemType.SEMI_MANUFACTURES_BASE),
    RAW_MATERIAL("400", PItemType.RAW_MATERIAL),
    PACKAGING_MATERIAL("500", PItemType.PACKAGING_MATERIAL),
    GOODS("600", PItemType.GOODS);

    private final String mrpCode;
    private final PItemType type;

    MaterialMRP(String mrpCode, PItemType type) {
        this.mrpCode = mrpCode;
        this.type = type;
    }

    public static MaterialMRP getByMRPCode(String mrpCode) {
        return Arrays.stream(MaterialMRP.values())
                .filter(mrp -> mrp.mrpCode.equals(mrpCode))
                .findAny()
                .orElse(null);
    }

    public static PItemType getTypeByMRPCode(String mrpCode) {
        MaterialMRP mrp = getByMRPCode(mrpCode);
        return mrp == null ? null : mrp.type;
    }

    public static boolean isDMRFinished(String mrpCode) {
        return FINISHED_SET.mrpCode.equals(mrpCode) || FINISHED_SINGLE.mrpCode.equals(mrpCode);
    }

    public static boolean isBulk(String mrpCode) {
        return SEMI_MANUFACTURES_BULK.mrpCode.equals(mrpCode);
    }

}