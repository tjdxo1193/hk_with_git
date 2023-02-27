package lims.api.integration.enums;

import java.util.Arrays;

public enum RevInterface {
    SAP_BOM(InterfaceSystemType.SAP, "IF_MD_0005", InterfaceSendType.SYNC),
    SAP_MATERIAL(InterfaceSystemType.SAP, "IF_MD_0020", InterfaceSendType.SYNC),
    SAP_TEST_REQUEST(InterfaceSystemType.SAP, "IF_LE_0704", InterfaceSendType.SYNC),
    SAP_CALENDAR(InterfaceSystemType.SAP, "IF_PP_0023", InterfaceSendType.SYNC),
    SAP_CHARACTERISTIC(InterfaceSystemType.SAP, "IF_MD_0014", InterfaceSendType.SYNC),
    SAP_INPUT_PERFORMANCE(InterfaceSystemType.SAP, "IF_LE_0707", InterfaceSendType.SYNC),

    ELN_CT_REPORT(InterfaceSystemType.ELN, "IF_LIMS_0006", InterfaceSendType.SYNC),
    ELN_STANDARD_FINISH_AND_SEMI(InterfaceSystemType.ELN, "IF_LIMS_0004", InterfaceSendType.SYNC),

    SRM_FINAL_ORDER(InterfaceSystemType.SRM, "IF_LIMS_0010", InterfaceSendType.SYNC),
    SRM_REOCCUR_PREVENT_REPORT(InterfaceSystemType.SRM, "IF_LIMS_0001", InterfaceSendType.SYNC),
    SRM_CONSIGNMENT_AND_SUPPLIER_REPORT(InterfaceSystemType.SRM, "IF_LIMS_0003", InterfaceSendType.SYNC),

    MES_FINAL_ORDER(InterfaceSystemType.MES, "IF_LIMS_0011", InterfaceSendType.SYNC),
    MES_PACKAGE_SPEC_REPORT(InterfaceSystemType.MES, "IF_LIMS_0007", InterfaceSendType.SYNC);

    private final InterfaceSystemType systemType;
    private final String interfaceId;
    private final InterfaceSendType sendType;

    RevInterface(InterfaceSystemType systemType, String interfaceId, InterfaceSendType sendType) {
        this.systemType = systemType;
        this.interfaceId = interfaceId;
        this.sendType = sendType;
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

    public InterfaceSendType getSendType() {
        return sendType;
    }
}