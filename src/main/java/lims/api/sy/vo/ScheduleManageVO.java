package lims.api.sy.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ScheduleManageVO{
    private String ident;
    private String year;
    private String month;
    private String day;
    private String caldDesc;
    private String workYn;
    private String caldDt;
    private String crtDs;
}

