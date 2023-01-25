package lims.api.integration.vo;

import lims.api.util.process.MappingKey;
import lims.api.integration.domain.eai.RevStateful;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SAPCalendarVO extends RevStateful {

    @MappingKey
    private String guid;
    @MappingKey
    private String seq;
    private String ident;
    private String ltext;
    private String year;
    private String month;
    private String day;
    private String date;
    private String work;
}