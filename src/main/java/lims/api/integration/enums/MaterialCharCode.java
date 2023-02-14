package lims.api.integration.enums;

public enum MaterialCharCode {
    BRD_ABBR("ZMDE_BRANDCD"),               // 브랜드 약어
    USE_TRM("ZMDE_TOTAL_SHELF_LIFE"),   // 제품에 표기하는 유통기한
    RMTR_SPEC("ZMDE_PHARMACOPOEIA"),        // 원료 규격
    NBR("ZMDE_COLOR_NUMBER"),               // 호수
    FTN_YN("ZMDE_FUNCTIONAL_GOODS"),        // 특성구분(기능성)
    PCS01("ZMDE_OPERATION_MEMO1"),          // 공정1
    PCS02("ZMDE_OPERATION_MEMO2"),          // 공정2
    PCS03("ZMDE_OPERATION_MEMO3"),          // 공정3
    OTC_PRD("ZMDE_OTC_PROD"),               // OTC 제품
    DMS_EPT_YN("ZMDE_DOM_EXP"),             // 내부/수출 여부
    PEAR_DIV("ZMDE_INCL_PEARL"),            // 펄 구분
    MKR_VOL("ZMDE_VOL_WEI"),                // 표시 용량
    MKR_VOL_UNIT("ZMDE_VOL_WEI_UNIT"),      // 표시 용량 단위
    ETN_ANS_REQ("ZMDE_REQ_EXT_TEST"),       // 외부 시험 의뢰
    CTRPT_NO("ZMDE_CT_NO"),                 // CT성적서 번호
    DIO_YN("ZMDE_DIOXANE_MGMT_TGT"),        // 디옥산 관리 대상
    PRB_IN_YN("ZMDE_INCL_PARABEN"),         // 파라벤 포함 여부
    PRB_FE_YN("ZMDE_PARABEN_MGMT_TGT"),     // 파라벤 프리 관리 대상
    PNX_FE_YN("ZMDE_EGPHE_MGMT_TGT");       // 페녹시 에탄올 프리 관리 대상

    private final String code;

    MaterialCharCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return code;
    }

}