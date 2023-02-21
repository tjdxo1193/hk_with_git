package lims.api.sy.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditManageColumn {

    private String tableNm;
    private String columnNm;
    private String columnComment;

}