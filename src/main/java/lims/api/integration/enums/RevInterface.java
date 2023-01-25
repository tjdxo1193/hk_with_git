package lims.api.integration.enums;

import java.util.Arrays;

public enum RevInterface {
    SAP_BOM(InterfaceSystemType.SAP, "IF_MD_0005"),
    SAP_MATERIAL(InterfaceSystemType.SAP, "IF_MD_0020"),
    SAP_TEST_REQUEST(InterfaceSystemType.SAP, "IF_LE_0704"),
    SAP_CALENDAR(InterfaceSystemType.SAP, "IF_PP_0023"),
    SAP_CHARACTERISTIC(InterfaceSystemType.SAP, "IF_MD_0014"),
    SAP_INPUT_PERFORMANCE(InterfaceSystemType.SAP, "IF_LE_0707"),

    ELN_CT_REPORT(InterfaceSystemType.ELN, "IF_LIMS_0006"),
    ELN_STANDARD_FINISH_AND_SEMI(InterfaceSystemType.ELN, "IF_LIMS_0004"),

    SRM_FINAL_ORDER(InterfaceSystemType.SRM, "IF_LIMS_0010"),
    SRM_REOCCUR_PREVENT_REPORT(InterfaceSystemType.SRM, "IF_LIMS_0001"),
    SRM_CONSIGNMENT_AND_SUPPLIER_REPORT(InterfaceSystemType.SRM, "IF_LIMS_0003"),

    MES_FINAL_ORDER(InterfaceSystemType.MES, "IF_LIMS_0011"),
    MES_PACKAGE_SPEC_REPORT(InterfaceSystemType.MES, "IF_LIMS_0007");

    private final InterfaceSystemType systemType;
    private final String interfaceId;

    RevInterface(InterfaceSystemType systemType, String interfaceId) {
        this.systemType = systemType;
        this.interfaceId = interfaceId;
    }

    public static RevInterface of(String name) {
        return Arrays.stream(RevInterface.values())
                .filter(processor -> processor.interfaceId.equals(name))
                .findAny()
                .orElse(null);
    }

    public String getSystemTypeString() {
        return systemType.name();
    }

    public String getId() {
        return interfaceId;
    }
}