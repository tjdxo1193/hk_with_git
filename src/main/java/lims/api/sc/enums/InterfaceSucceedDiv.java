package lims.api.sc.enums;

import lombok.Getter;

@Getter
public enum InterfaceSucceedDiv {
    NONE("S0340001"),
    SUCCEED("S0340002"),
    FAIL("S0340003");

    private final String type;

    InterfaceSucceedDiv(String type) {
        this.type = type;
    }

    public boolean equals(InterfaceSucceedDiv type) {
        return this == type;
    }
}
