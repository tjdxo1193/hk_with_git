package lims.api.integration.enums.rfc;

public enum RFCParamOfAssetsDepreciation {
    I_ANLKL(8, String.class, true),  // 자산클래스
    I_BZDAT(8, String.class);                // 자산 기준일

    private final int size;
    private final Class<?> valueType;
    private final boolean required;

    RFCParamOfAssetsDepreciation(int size, Class<?> valueType) {
        this.size = size;
        this.valueType = valueType;
        this.required = false;
    }


    RFCParamOfAssetsDepreciation(int size, Class<?> valueType, boolean required) {
        this.size = size;
        this.valueType = valueType;
        this.required = required;
    }

}