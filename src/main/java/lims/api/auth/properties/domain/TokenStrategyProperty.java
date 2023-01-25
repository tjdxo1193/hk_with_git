package lims.api.auth.properties.domain;

public enum TokenStrategyProperty {
    COOKIE,
    HEADER;

    public boolean equals(String value) {
        return value != null && this.name().equals(value.toUpperCase());
    }
}