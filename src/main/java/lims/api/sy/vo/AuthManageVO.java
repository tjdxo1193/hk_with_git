package lims.api.sy.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthManageVO implements UpdateDetect {
    /* SY_ATH_GP */
    private String plntCd;      // 사업장 코드
    private String athCd;       // 권한 코드
    private String menuCd;      // 메뉴 코드
    private String useYn;       // 사용 여부
    private String crtIp;       // 생성 IP
    private String crtDs;       // 생성 일시
    private String crtUid;      // 생성 UID
    private String udtIp;       // 수정 IP
    private String udtDs;       // 수정 일시
    private String udtUid;      // 수정 UID

    /* SY_ATH */
    private String athNm;       // 권한 명
    private String athdesc;     // 권한 설명
    private Integer ord;        // 정렬 순서
    private Character ansYn;
    private Character revwYn;
    private Character aprYn;

    /* SY_MENU */
    private String hirMenuCd;   // 상위 메뉴 코드
    private String menuUrl;     // 메뉴 URL
    private String menuNm;      // 메뉴 명
    private Integer menuOrd;    // 메뉴 정렬 순서
    private String menuDesc;    // 메뉴 설명
    private String menuPrtYn;   // 메뉴 출력 여부

    /* Alias */
    private String hirMenuNm;   // 상위 메뉴 명
    private List<AuthManageVO> addedRowItems;   // 추가된 데이터
    private List<AuthManageVO> editedRowItems;  // 수정된 데이터
    private List<AuthManageVO> removedRowItems; // 삭제된 데이터
}
