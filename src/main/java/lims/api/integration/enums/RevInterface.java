package lims.api.integration.enums;

import java.util.Arrays;

public enum RevInterface {
    SAP_BOM(InterfaceSystemType.SAP, "IF_MD_0005", InterfaceSendType.SYNC, "BOM 마스터"),
    SAP_MATERIAL(InterfaceSystemType.SAP, "IF_MD_0020", InterfaceSendType.SYNC, "자재 마스터"),
    SAP_TEST_REQUEST(InterfaceSystemType.SAP, "IF_LE_0704", InterfaceSendType.SYNC, "시험 의뢰"),
    SAP_CALENDAR(InterfaceSystemType.SAP, "IF_PP_0023", InterfaceSendType.SYNC, "근무 달력"),
    SAP_CHARACTERISTIC(InterfaceSystemType.SAP, "IF_MD_0014", InterfaceSendType.SYNC, "자재 특성 명세"),
    SAP_INPUT_PERFORMANCE(InterfaceSystemType.SAP, "IF_LE_0707", InterfaceSendType.SYNC, "투입 실적 내역"),

    ELN_CT_REPORT(InterfaceSystemType.ELN, "IF_LIMS_0006", InterfaceSendType.SYNC, "CT 성적서"),
    ELN_STANDARD_FINISH_AND_SEMI(InterfaceSystemType.ELN, "IF_LIMS_0004", InterfaceSendType.SYNC, "기준 규격(완제품, 반제품)"),

    SRM_FINAL_ORDER(InterfaceSystemType.SRM, "IF_LIMS_0010", InterfaceSendType.SYNC, "구매 오더 마감"),
    SRM_REOCCUR_PREVENT_REPORT(InterfaceSystemType.SRM, "IF_LIMS_0001", InterfaceSendType.SYNC, "재발 방지 대책서"),
    SRM_CONSIGNMENT_AND_SUPPLIER_REPORT(InterfaceSystemType.SRM, "IF_LIMS_0003", InterfaceSendType.SYNC, "공급처/위탁 성적서"),

    MES_FINAL_ORDER(InterfaceSystemType.MES, "IF_LIMS_0011", InterfaceSendType.SYNC, "생산 오더 마감"),
    MES_PACKAGE_SPEC_REPORT(InterfaceSystemType.MES, "IF_LIMS_0007", InterfaceSendType.SYNC, "포장사양서");

    private final InterfaceSystemType systemType;
    private final String interfaceId;
    private final InterfaceSendType sendType;
    private final String desc;

    RevInterface(InterfaceSystemType systemType, String interfaceId, InterfaceSendType sendType, String desc) {
        this.systemType = systemType;
        this.interfaceId = interfaceId;
        this.sendType = sendType;
        this.desc = desc;
    }

    public static RevInterface of(String name) {
        return Arrays.stream(RevInterface.values())
                .filter(processor -> processor.interfaceId.equals(name))
                .findAny()
                .orElse(null);
    }

    public InterfaceSystemType getSystemType() {
        return systemType;
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

    public String getDesc() {
        return desc;
    }
}