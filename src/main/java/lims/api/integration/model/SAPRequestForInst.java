package lims.api.integration.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SAPRequestForInst {

    private String xtid;
    private String xifid;
    private String xdate;
    private String xtime;

}