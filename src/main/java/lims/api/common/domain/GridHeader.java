package lims.api.common.domain;

import lombok.Getter;

@Getter
public class GridHeader {

    private final String dataField;
    private final String headerText;

    public GridHeader(String dataField, String headerText) {
        this.dataField = dataField;
        this.headerText = headerText;
    }
}