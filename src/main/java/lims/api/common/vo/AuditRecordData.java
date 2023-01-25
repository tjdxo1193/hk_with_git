package lims.api.common.vo;

import lims.api.common.domain.AuditRowData;
import lombok.Getter;
import lombok.Setter;
import spring.audit.type.CommandType;

@Getter
@Setter
public class AuditRecordData {

    private CommandType commandType;
    private String label;
    private AuditRowData beforeData;
    private AuditRowData afterData;

}