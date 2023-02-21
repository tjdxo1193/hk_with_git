package lims.api.gl.vo;

import lims.api.common.domain.FileKey;
import lims.api.common.domain.UpdateDetect;
import lims.api.gl.enums.GlassMaterialProcess;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GlassMaterialVO implements UpdateDetect {
    private String plntCd;
    private String ritmCd;
    private String upperRitmTreeCd;
    private String upperRitmTreeNm;
    private String ritmTreeCd;
    private String ritmTreeNm;
    private String ritmKn;
    private String ritmEn;
    private String ritmAbbr;
    private String ritmRmk;
    private String ritmCrgUid;
    private String ritmCrgNm;
    private String ritmCrgDptNm;
    private String ritmUnitCd;
    private String ritmUnitNm;
    private String strgTerms;
    private String strgTermsNm;
    private String strgPla;
    private String strgPlaNm;
    private String strgPlaDtl;
    private String mngNo;
    private Integer sfyStok;
    private String spec;
    private String casNo;
    private String fomu;
    private String shadeYn;
    private String mtrDedutYn;
    private String poisYn;
    private String dangYn;
    private Integer ritmEtrIdx;
    private String ritmRootCd;
    private String ritmRootNm;
    private String etrProcCd;
    private String etrProcNm;
    private Integer etrQty;
    private float etrEachQty;
    private float etrTotQty;
    private String vdrCd;
    private String vdrNm;
    private String vdrCrgNm;
    private String vdrCrgTel;
    private String makDt;
    private String makNo;
    private String etrDt;
    private String etrRmk;
    private String expirDtChk;
    private String opnBefExpirDt;
    private Integer opnAftExpirTrm;
    private String currLotYn;
    private Integer etrReqAprIdx;
    private String rjtUid;
    private String rjtDs;
    private String rjtRea;
    private String delUid;
    private String delDs;
    private String delRea;
    private String delYn;
    private String glsVol;
    private String glsVolUnitCd;
    private String glsVolUnitNm;
    private String glsColrMtr;
    private String crtIp;
    private String crtDs;
    private String crtUid;
    private String udtIp;
    private String udtDs;
    private String udtUid;
    private String useYn;
    private Integer ritmMngIdx;
    private Integer ritmEtrNo;
    private String mngProcCd;
    private String mngProcNm;
    private String ritmLabelNo;
    private String opnDt;
    private String expirDt;
    private String opnRmk;
    private String opnUid;
    private String opnDs;
    private String opnCanlUid;
    private String opnCanlDs;
    private String opnCanlRea;
    private Integer dpsReqAprIdx;
    private String dpsRea;
    private Integer useSeq;
    private String pitmNm;
    private String ansNo;
    private String aitmNm;
    private String useUid;
    private String useNm;
    private String useDt;
    private float useQty;
    private float compareUseQty;
    private String usePps;
    private String useRmk;
    private String aprReqUid;
    private String aprReqDiv;
    private String aprUid;
    private String aprYn;
    private List<String> searchMakDt;
    private List<String> searchEtrDt;
    private List<String> searchUseDt;
    private String etrReqAprYn;
    private Integer fileIdx;
    private GlassMaterialProcess processCode;
    private List<GlassMaterialProcess> processCodeList;
    private List<MultipartFile> addedFiles = new ArrayList<>();
    private List<FileKey> removedFileIds = new ArrayList<>();
    private String withDelegateUserIds;
    private String aprIp;
    private String reason;
    private String type;
    private String dpsDt;
    private String dpsUid;
    private String dpsNm;
    private String aprRea;
    private String sumStok;
    private String aprReqIp;
}
