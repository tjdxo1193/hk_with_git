package lims.api.integration.enums.rfc;

public enum RFCParamOfAssets {
    IV_ANLKL(8, String.class, true), // 자산클래스
    IV_GETDAT_FROM(8, String.class, true), // 조회적용일자(FROM)
    IV_GETDAT_TO(8, String.class, true); // 조회적용일자(TO)

    private final int size;
    private final Class<?> valueType;
    private final boolean required;

    RFCParamOfAssets(int size, Class<?> valueType, boolean required) {
        this.size = size;
        this.valueType = valueType;
        this.required = required;
    }
}