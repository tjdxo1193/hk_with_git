package lims.api.common.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditColumnData {

    private String data;
    private String columnComment;

    public AuditColumnData() {}

    public AuditColumnData(String data) {
        this.data = data;
    }

    public AuditColumnData(String data, String columnComment) {
        this.data = data;
        this.columnComment = columnComment;
    }
}
