package lims.api.common.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paging {
    private int offset;
    private int limit;
    private int total;
}