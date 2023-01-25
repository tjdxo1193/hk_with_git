package lims.api.common.enums;

public enum PlantType {
    SYSTEM("0000", "127.0.0.1", "");

    private final String code;
    private final String ip;
    private final String uid;

    PlantType(String code, String ip, String uid) {
        this.code = code;
        this.ip = ip;
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public String getIp() {
        return ip;
    }

    public String getUid() {
        return uid;
    }

}