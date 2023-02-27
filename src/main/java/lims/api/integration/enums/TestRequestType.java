package lims.api.integration.enums;

public enum TestRequestType {
    A, // 구매
    B, // 생산
    C; // 수동(= SAP에서 수동으로 보낸다는 의미. 즉 LIMS에서는 재시험)
}