package lims.api.integration.vo.rfc;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RFCAssetsVO {
    private String anln1; // 주요 자산 번호
    private String anln2; // 자산 하위 번호
    private String anlkl; // 자산 클래스
    private String txt50; // 자산내역(자산명)
    private String erdat; // 생성일
    private String aedat; // 변경일자
    private String zugdt; // 첫 취득일
    private String deakt; // 비활성화일
    private String txa50; // 추가설명
    private String zzpic; // 담당자사번
    private String kostl; // 코스트센터

    // 감가상각 정보
    private String kansw;   // 취득가액
    private String kumafa;  // 감가상각누계액
    private String bzdat;   // 자산기준일
}