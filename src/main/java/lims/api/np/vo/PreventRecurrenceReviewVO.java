package lims.api.np.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreventRecurrenceReviewVO implements UpdateDetect {
    private String plntCd; //사업장 코드
    private Integer pitmSpecIdx;
    private String pitmTyp;
    private String pitmTypNm;
    private String pitmCd;
}
