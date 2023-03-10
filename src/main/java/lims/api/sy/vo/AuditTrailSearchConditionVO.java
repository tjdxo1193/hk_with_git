package lims.api.sy.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuditTrailSearchConditionVO {
    private String plantCode;
    private String menuCode;
    private String ip;
    private List<String> searchRange;
}