package lims.api.lb.enums;

public enum ApproveRequestLbDivType {
    LAB_EVENT_SPEC_VERSION("S0050012");

    private final String code;

    ApproveRequestLbDivType(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
