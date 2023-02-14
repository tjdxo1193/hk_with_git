package lims.api.schedule.enums;

public enum WorkDay {
    WORK("1"),
    HOLIDAY("0");

    private String code;

    WorkDay(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}