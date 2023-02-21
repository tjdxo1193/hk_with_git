package lims.api.integration.enums;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum RfcInterface {
    ASSETS("ZFI_IF_O_ASSET_MASTER"),
    ASSETS_DEPRECIATION("ZFI_IF_O_ASSET_DEPR"),
    BUSINESS_PARTNER("ZFI_IF_I_BP_CHECK");

    private final String functionName;

    RfcInterface(String functionName) {
        this.functionName = functionName;
    }

    public static RfcInterface of(String name) {
        return Arrays.stream(RfcInterface.values())
                .filter(rfcInterface -> rfcInterface.getFunctionName().equals(name))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("not found RfcInterface of '" + name + "'"));
    }

    public String getFunctionName() {
        return functionName;
    }
}