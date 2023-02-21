package lims.api.integration.enums.rfc;

public enum RFCParamOfBusinessPartner {
    CHECK(2, RFCBusinessPartnerCheck.class),
    PARTNER(10, String.class),        // BP 번호
    BU_GOURP(4, String.class),        // BP 그룹
    LAND1(3, String.class),           // 국가키
    NAME_ORG1(40, String.class),      // 이름1
    BU_SORT1_TXT(20, String.class),   // 검색어1
    TAXNUM1(60, String.class),        // 주민등록번호
    TAXNUM2(60, String.class);        // 사업자등록번호

    private final int size;
    private final Class<?> valueType;

    RFCParamOfBusinessPartner(int size, Class<?> valueType) {
        this.size = size;
        this.valueType = valueType;
    }

}