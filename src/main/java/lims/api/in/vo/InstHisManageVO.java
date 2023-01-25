package lims.api.in.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lims.api.common.domain.FileKey;
import lims.api.common.domain.UpdateDetect;
import lims.api.common.vo.ApproveVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstHisManageVO implements UpdateDetect {
	private String plntCd;
	private String eqmCd;
	private Integer hisSeq;
	private String eqmNm;
	private String eqmDiv;
	private String eqmDivNm;
	private String eqmCrst;
	private String eqmCrstNm;
	private String srlNo;
	private String makComp;
	private String splComp;
	private String modNm;
	private String crgUid;
	private String crgNm;
	private String IQ;
	private String OQ;
	private String PQ;
	private String chkCyl;
	private String quaCyl;
	private String calCyl;
	private String sapAstNo;
	private String eqmHisDiv;
	private String eqmHisDivNm;
	private String eqmHisProcCd;
	private String eqmHisProcNm;
	private Integer eqmHisAprIdx;
	private String rjtUid;
	private String rjtNm;
	private String rjtDs;
	private String rjtRea;
	private String delYn;
	private String rgtUid;
	private String rgtNm;
	private String rgtDt;
	private String ansDt;
	private String rmk;
	private Integer hisFileIdx;
	private ApproveVO approveInfo;
	private List<MultipartFile> addedFiles = new ArrayList<>();
	private List<FileKey> removedFileIds = new ArrayList<>();
}
