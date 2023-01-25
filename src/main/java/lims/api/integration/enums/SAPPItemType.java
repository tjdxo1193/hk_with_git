package lims.api.integration.enums;

import lims.api.ms.enums.PItemType;

public enum SAPPItemType {
    FERT("완제품", PItemType.FINISHED_SET, PItemType.FINISHED_SINGLE, PItemType.FINISHED_BEAUTIFUL_PACKAGING),
    HALB("반제품", PItemType.SEMI_MANUFACTURES_BASE, PItemType.SEMI_MANUFACTURES_BULK, PItemType.SEMI_MANUFACTURES_FILLING_FOAM, PItemType.SEMI_MANUFACTURES_OTHER_PRODUCT),
    ROH1("원료", PItemType.RAW_MATERIAL),
    ROH2("원료(무상)", PItemType.RAW_MATERIAL),
    ROH3("포장재", PItemType.PACKAGING_MATERIAL),
    ROH4("포장재(무상)", PItemType.PACKAGING_MATERIAL),
    HAWA("상품", PItemType.GOODS);

    private final PItemType[] type;

    SAPPItemType(String name, PItemType... type) {
        this.type = type;
    }

    public static boolean isFinished(String code) {
        return FERT.name().equals(code);
    }

    public static boolean isPackaging(String code) {
        return ROH3.name().equals(code) || ROH4.name().equals(code);
    }

    public static boolean isSemi(String code) {
        return HALB.name().equals(code);
    }

}