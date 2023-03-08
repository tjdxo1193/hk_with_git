package lims.api.integration.enums.rfc;

public enum RFCParamOfAssets {
    IV_ANLKL(8, String.class), // 자산클래스
    IV_GETDAT_FROM(8, String.class), // 조회적용일자(FROM)
    IV_GETDAT_TO(8, String.class); // 조회적용일자(TO)

    private final int size;
    private final Class<?> valueType;

    RFCParamOfAssets(int size, Class<?> valueType) {
        this.size = size;
        this.valueType = valueType;
    }
}