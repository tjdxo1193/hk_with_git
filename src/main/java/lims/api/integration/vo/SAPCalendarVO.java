package lims.api.integration.vo;

import lims.api.integration.annotation.NotMapping;
import lims.api.util.process.KeyGenerator;
import lims.api.util.process.MappingKey;
import lims.api.integration.domain.eai.RevStateful;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {  })
public class SAPCalendarVO extends RevStateful implements KeyGenerator {

    @MappingKey
    private String guid;
    @MappingKey
    private String seq;
    private String ident;
    private String ltext;
    private String year;
    private String month;
    private String date;
    private String work;

    @NotMapping
    private String day;

    public String getConcatenateDate() {
        return year + "-" + month + "-" + date;
    }
}