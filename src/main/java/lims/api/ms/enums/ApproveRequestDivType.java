package lims.api.ms.enums;
public enum ApproveRequestDivType {
    TEST_MATERIAL_WAREHOUSING("S0050001"),
    TEST_MATERIAL_DISPOSAL("S0050002"),
    PACKAGING_TEST_VERSION("S0050003"),
    MONITORING_SPEC_VERSION("S0050004"),
    ITEM_MASTER_VERSION("S0050005"),
    ITEM_TEST_SPEC_VERSION("S0050006"),
    LAB_EVENT_SPEC_VERSION("S0050012");
    private final String code;

    ApproveRequestDivType(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

}
