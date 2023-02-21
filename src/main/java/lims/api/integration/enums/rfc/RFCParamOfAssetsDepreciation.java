package lims.api.integration.enums.rfc;

public enum RFCParamOfAssetsDepreciation {
    I_ANLKL(8, String.class);        // 자산클래스

    private final int size;
    private final Class<?> valueType;

    RFCParamOfAssetsDepreciation(int size, Class<?> valueType) {
        this.size = size;
        this.valueType = valueType;
    }

}