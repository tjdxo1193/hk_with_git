package lims.api.ms.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemApprSpecVO {
    private String plntCd;
    private String pitmCd;
    private Integer pitmVer;
    private String specProcCd;
    private String delYn;
    private String useVerYn;
    private String rvsNo;
    private String docNo;
    private String rvsDt;
    private String enfoDt;
    private String rvsReaCd;
    private String rvsCtt;
    private String rvsDivPs;
    private String revwUid;
    private String revwDs;
    private String revwIp;
    private String pitmMstAprIdx;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;
    private Integer aitmSpecIdx;
    private Integer oldAitmSpecIdx;
    private Integer pitmSpecIdx;
    private Integer aitmSpecVer;
}
