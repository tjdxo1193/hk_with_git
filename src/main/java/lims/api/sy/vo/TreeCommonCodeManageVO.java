package lims.api.sy.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TreeCommonCodeManageVO implements UpdateDetect {

    private String plntCd;
    private String treeCd;
    private String hirTreeCd;
    private String treeNm;
    private String treeEn;
    private String treeAbbr;
    private Integer treeCdOrd;
    private String useYn;
    private String treeRmk;
    private Integer deptLmt;

    private List<TreeCommonCodeManageVO> addedList = new ArrayList<>();
    private List<TreeCommonCodeManageVO> updatedList = new ArrayList<>();

}