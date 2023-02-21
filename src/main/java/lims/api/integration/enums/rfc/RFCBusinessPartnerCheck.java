package lims.api.integration.enums.rfc;

public enum RFCBusinessPartnerCheck {
    RECENT_10_DAYS("02"),           // 최근 10일 내 변경사항 및 신규 추가 건 목록 조회. 특정 필드로 조건을 주는 것은 불가능. 따라서 항상 동일한 목록 반환.
    SEARCH_BY_CONDITION("03"),     // 특정 조건 값을 주고 조회하고자 할 때.
    ALL_FOR_MIGRATION("99");        // 마이그레이션 용도. 전체 목록을 조회.

    private final String code;

    RFCBusinessPartnerCheck(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}