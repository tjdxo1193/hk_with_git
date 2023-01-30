package lims.api.ms.enums;

public enum ELNProductDiv {
    F(PItemType.SEMI_MANUFACTURES_OTHER_PRODUCT, PItemType.SEMI_MANUFACTURES_FILLING_FOAM),
    S(PItemType.SEMI_MANUFACTURES_BULK, PItemType.SEMI_MANUFACTURES_BASE);
    private final PItemType[] type;
    ELNProductDiv(PItemType... type) {
        this.type = type;
    }
    public static boolean isELNFinished(String code){ return F.name().equals(code); }
    public static boolean isELNSemi(String code){ return S.name().equals(code); }

    public static ELNProductDiv of(String name) {
        for (ELNProductDiv div : ELNProductDiv.values()) {
            if (div.name().equals(name)) {
                return div;
            }
        }
        return null;
    }
}
