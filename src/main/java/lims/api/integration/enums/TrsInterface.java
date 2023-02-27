package lims.api.integration.enums;

public enum TrsInterface {
    SAP_TEST_STATUS(InterfaceSystemType.SAP, "IF_LE_0705", "/RESTAdapter/PIF_LE_0705", InterfaceSendType.SYNC),
    SAP_TEST_RESULT(InterfaceSystemType.SAP, "IF_LE_0706", "/RESTAdapter/PIF_LE_0706", InterfaceSendType.SYNC),
    SAP_ASSETS_MOVEMENT_HISTORY(InterfaceSystemType.SAP, "IF_FI_0086", "/RESTAdapter/PIF_FI_0086", InterfaceSendType.SYNC),
    SAP_TEST_PERFORM_OF_INBOUND_PURCHASE(InterfaceSystemType.SAP, "IF_CO_0009", "/RESTAdapter/PIF_CO_0009", InterfaceSendType.SYNC),
    SAP_TEST_PERFORM_OF_INBOUND_MANUFACTURE(InterfaceSystemType.SAP, "IF_PP_0033", "/RESTAdapter/PIF_PP_0033", InterfaceSendType.SYNC),

    SRM_TEST_STATUS(InterfaceSystemType.SRM, "IF_SRM_0007", "/RESTAdapter/PIF_SRM_0007", InterfaceSendType.SYNC),
    SRM_TEST_RESULT(InterfaceSystemType.SRM, "IF_SRM_0008", "/RESTAdapter/PIF_SRM_0008", InterfaceSendType.SYNC),
    SRM_NON_CFM_REPORT(InterfaceSystemType.SRM, "IF_SRM_0004", "/RESTAdapter/PIF_SRM_0004", InterfaceSendType.SYNC),

    MES_TEST_STATUS(InterfaceSystemType.MES, "IF_MES_0012", "/RESTAdapter/PIF_MES_0012", InterfaceSendType.SYNC),
    MES_TEST_RESULT(InterfaceSystemType.MES, "IF_MES_0013", "/RESTAdapter/PIF_MES_0013", InterfaceSendType.SYNC),
    MES_NON_CFM_REPORT(InterfaceSystemType.MES, "IF_MES_0006", "/RESTAdapter/PIF_MES_0006", InterfaceSendType.SYNC),

    ELN_METHOD_BY_ITEM(InterfaceSystemType.ELN, "IF_ELN_0008", "/RESTAdapter/PIF_ELN_0008", InterfaceSendType.ASYNC),

    QMS_MATERIAL(InterfaceSystemType.QMS, null, null, InterfaceSendType.SYNC),
    QMS_SHIPT(InterfaceSystemType.QMS, null, "/interface/lims/shipt.qms", InterfaceSendType.SYNC);

    private final InterfaceSystemType systemType;
    private final String interfaceId;
    private final String eaiServicePath;
    private final InterfaceSendType sendType;

    TrsInterface(InterfaceSystemType systemType, String interfaceId, String eaiServicePath, InterfaceSendType sendType) {
        this.systemType = systemType;
        this.interfaceId = interfaceId;
        this.eaiServicePath = eaiServicePath;
        this.sendType = sendType;
    }

    public String getSystemTypeString() {
        return systemType.name();
    }

    public String getId() {
        return interfaceId;
    }

    public String getServicePath() {
        return eaiServicePath;
    }

    public InterfaceSendType getSendType() {
        return sendType;
    }
}