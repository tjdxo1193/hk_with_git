package lims.api.sy.model;

import lims.api.common.domain.GridHeader;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class AuditTrailSearchResponse {

    private final List<GridHeader> headerDataFields;
    private final List<Map<String, Object>> data;

    public AuditTrailSearchResponse(List<GridHeader> headerDataFields, List<Map<String, Object>> data) {
        this.headerDataFields = headerDataFields;
        this.data = data;
    }
}
