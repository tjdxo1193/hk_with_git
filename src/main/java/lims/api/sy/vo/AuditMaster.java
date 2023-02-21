package lims.api.sy.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditMaster {
    private String menuCd;
    private Integer seqno;
    private String menuNm;
    private String tableNm;
    private String columnNm;
    private String columnComment;
    private String columnHeaderNm;
    private String joinTableNm;
    private String joinColumnNm;
    private String joinDisplayColumnNm;
}