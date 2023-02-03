package lims.api.st.enums;

public enum ApproveRequestStDivType {
    STAB_PLAN_VERSION("S0050019");

    private final String code;

    ApproveRequestStDivType(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
