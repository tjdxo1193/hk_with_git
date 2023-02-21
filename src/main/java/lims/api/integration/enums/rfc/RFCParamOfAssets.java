package lims.api.integration.enums.rfc;

public enum RFCParamOfAssets {
    IV_ANLKL(8, String.class); // 자산클래스

    private final int size;
    private final Class<?> valueType;

    RFCParamOfAssets(int size, Class<?> valueType) {
        this.size = size;
        this.valueType = valueType;
    }
}