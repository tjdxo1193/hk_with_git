package lims.api.sc.enums;

import lombok.Getter;

@Getter
public enum InterfaceTransferDiv {
    AUTO("S0330001"),
    MANUAL("S0330002");

    private final String transferDiv;

    InterfaceTransferDiv(String transferDiv) {
        this.transferDiv = transferDiv;
    }

    public boolean equals(InterfaceTransferDiv transferDiv) {
        return this == transferDiv;
    }
}
