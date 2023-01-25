package lims.api.sy.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuditManageTable {

    private String tableNm;
    private String tableComment;
    private List<AuditManageColumn> columns;

}