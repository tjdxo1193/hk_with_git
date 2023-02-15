package lims.api.integration.enums.rfc;

public enum RFCAssetsDepreciation {
    ANLKL(8, String.class);        // 자산클래스

    private final int size;
    private final Class<?> valueType;

    RFCAssetsDepreciation(int size, Class<?> valueType) {
        this.size = size;
        this.valueType = valueType;
    }

}