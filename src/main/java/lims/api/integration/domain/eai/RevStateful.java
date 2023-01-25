package lims.api.integration.domain.eai;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RevStateful {
    private Integer idx;
    private Integer degree;
    private Integer ifInfoIdx;
    private String crtDs;

    public RevStateful() {}

    public RevStateful(Integer degree, Integer ifInfoIdx) {
        this.degree = degree;
        this.ifInfoIdx = ifInfoIdx;
    }
}