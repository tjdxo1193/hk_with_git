package lims.api.ms.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MsElnCtRptFileKey {
    private String ctId;
    private String ctSeq;

    public MsElnCtRptFileKey() {};

    public MsElnCtRptFileKey(String ctId, String ctSeq) {
        this.ctId = ctId;
        this.ctSeq = ctSeq;
    }
}
