package lims.api.integration.model;

import lims.api.integration.vo.SAPCalendarVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SAPRequestForCalendar extends InterfaceInfo {

    private List<SAPCalendarVO> dataList;

}