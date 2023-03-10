package lims.api.integration.enums;

public enum TrsInterface {
    SAP_TEST_STATUS(InterfaceSystemType.SAP, "IF_LE_0705", "/RESTAdapter/PIF_LE_0705", InterfaceSendType.SYNC, "품질시험 진행 상태"),
    SAP_TEST_RESULT(InterfaceSystemType.SAP, "IF_LE_0706", "/RESTAdapter/PIF_LE_0706", InterfaceSendType.SYNC, "품질시험 결과 판정"),
    SAP_ASSETS_MOVEMENT_HISTORY(InterfaceSystemType.SAP, "IF_FI_0086", "/RESTAdapter/PIF_FI_0086", InterfaceSendType.SYNC, "유무형 자산 이동 내역"),
    SAP_TEST_PERFORM_OF_INBOUND_PURCHASE(InterfaceSystemType.SAP, "IF_CO_0009", "/RESTAdapter/PIF_CO_0009", InterfaceSendType.SYNC, "구매 입고 품질 검사 횟수"),
    SAP_TEST_PERFORM_OF_INBOUND_MANUFACTURE(InterfaceSystemType.SAP, "IF_PP_0033", "/RESTAdapter/PIF_PP_0033", InterfaceSendType.SYNC, "생산 입고 품질 검사 횟수"),

    SRM_TEST_STATUS(InterfaceSystemType.SRM, "IF_SRM_0007", "/RESTAdapter/PIF_SRM_0007", InterfaceSendType.SYNC, "품질시험 진행 상태"),
    SRM_TEST_RESULT(InterfaceSystemType.SRM, "IF_SRM_0008", "/RESTAdapter/PIF_SRM_0008", InterfaceSendType.SYNC, "품질시험 결과 판정"),
    SRM_NON_CFM_REPORT(InterfaceSystemType.SRM, "IF_SRM_0004", "/RESTAdapter/PIF_SRM_0004", InterfaceSendType.SYNC, "부적합 보고서"),

    MES_TEST_STATUS(InterfaceSystemType.MES, "IF_MES_0012", "/RESTAdapter/PIF_MES_0012", InterfaceSendType.SYNC, "품질시험 진행 상태"),
    MES_TEST_RESULT(InterfaceSystemType.MES, "IF_MES_0013", "/RESTAdapter/PIF_MES_0013", InterfaceSendType.SYNC, "품질시험 결과 판정"),
    MES_NON_CFM_REPORT(InterfaceSystemType.MES, "IF_MES_0006", "/RESTAdapter/PIF_MES_0006", InterfaceSendType.SYNC, "부적합 보고서"),

    ELN_METHOD_BY_ITEM(InterfaceSystemType.ELN, "IF_ELN_0008", "/RESTAdapter/PIF_ELN_0008", InterfaceSendType.ASYNC, "시험항목별 시험방법"),

    QMS_MATERIAL(InterfaceSystemType.QMS, null, "/interface/lims/material.qms", InterfaceSendType.SYNC, "제품표준서 자재마스터 (완제품, 벌크)"),
    QMS_SHIPT(InterfaceSystemType.QMS, null, "/interface/lims/shipt.qms", InterfaceSendType.SYNC, "출하 승인 요청");

    private final InterfaceSystemType systemType;
    private final String interfaceId;
    private final String eaiServicePath;
    private final InterfaceSendType sendType;
    private final String desc;

    TrsInterface(InterfaceSystemType systemType, String interfaceId, String eaiServicePath, InterfaceSendType sendType, String desc) {
        this.systemType = systemType;
        this.interfaceId = interfaceId;
        this.eaiServicePath = eaiServicePath;
        this.sendType = sendType;
        this.desc = desc;
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

    public String getServicePath() {
        return eaiServicePath;
    }

    public InterfaceSendType getSendType() {
        return sendType;
    }

    public String getDesc() {
        return desc;
    }
}