package lims.api.integration.vo.rfc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RFCAssetsDepreciationVO {
    private String bukrs;   // 회사 코드
    private String anln1;   // 주요 자산 번호
    private String anln2;   // 자산 하위 번호
    private String anlkl;   // 자산 클래스
    private String kansw;   // 취득가액
    private String kumafa;  // 감가상각누계액
    private String bzdat;   // 자산기준일
}