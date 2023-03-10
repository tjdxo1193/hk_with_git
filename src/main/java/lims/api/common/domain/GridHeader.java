package lims.api.common.domain;

import lombok.Getter;

@Getter
public class GridHeader {

    private final String dataField;
    private final String headerText;
    private final boolean visible;

    public GridHeader(String dataField, String headerText) {
        this.dataField = dataField;
        this.headerText = headerText;
        this.visible = true;
    }

    public GridHeader(String dataField, String headerText, boolean visible) {
        this.dataField = dataField;
        this.headerText = headerText;
        this.visible = visible;
    }
}