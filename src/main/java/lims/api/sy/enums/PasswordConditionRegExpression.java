package lims.api.sy.enums;

public enum PasswordConditionRegExpression {
    NUMBER(".*\\d.*"),
    KOREAN(".*[가-힣].*"),
    ENGLISH(".*[a-zA-Z].*"),
    SPECIAL_CHARACTERS(".*[`~!@#$%^&*|;:?()<>\\-].*"),
    BLANK(".*[\\s].*"),
    TRIPLE_LETTER(".*(.)\\1\\1.*");
    private final String regExpression;
    PasswordConditionRegExpression(String regExpression) {
        this.regExpression = regExpression;
    }
    public String getValue() {
        return regExpression;
    }
}
