package lims.api.sy.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuditMenu {

    private String code;
    private String name;
    private List<AuditMenu> children;

}