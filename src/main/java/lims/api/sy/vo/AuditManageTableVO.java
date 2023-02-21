package lims.api.sy.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditManageTableVO {

    private String owner;
    private String tableNm;
    private String tableComment;

}