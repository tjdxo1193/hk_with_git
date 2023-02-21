package lims.api.integration.enums;

public enum PurchaseInboundMonth {
    JANUARY("001"),
    FEBRUARY("002"),
    MARCH("003"),
    APRIL("004"),
    MAY("005"),
    JUNE("006"),
    JULY("007"),
    AUGUST("008"),
    SEPTEMBER("009"),
    OCTOBER("010"),
    NOVEMBER("011"),
    DECEMBER("012");

    private final String monthCode;

    PurchaseInboundMonth(String code) {
        this.monthCode = code;
    }

    public String getValue() {
        return monthCode;
    }
}