package lims.api.integration.service;

import lims.api.integration.enums.CompanyType;
import lims.api.integration.enums.MaterialCharCode;
import lims.api.integration.vo.SAPBusinessPartnerVO;
import lims.api.integration.vo.SAPMaterialVO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MaterialAdditionAttribute {

    private final Map<String, String> materialName;
    private final Map<MaterialKey, String> additionAttr;
    private final Map<String, String> businessPartnerName;

    public MaterialAdditionAttribute(List<SAPMaterialVO.Zmdv> zmdv, List<SAPMaterialVO.Makt> makt,
                                     List<SAPBusinessPartnerVO> businessPartners) {
        this.materialName = toNameMap(makt);
        this.additionAttr = toAdditionAttrMap(zmdv);
        this.businessPartnerName = toBusinessPartnerNameMap(businessPartners);
    }

    private Map<String, String> toNameMap(List<SAPMaterialVO.Makt> data) {
        return data.stream()
                .filter(o -> "3".equals(o.getSpras())) // 3이 한글인지 체크??
                .collect(Collectors.toMap(SAPMaterialVO.Makt::getMatnr, SAPMaterialVO.Makt::getMaktx));
    }

    private Map<MaterialKey, String> toAdditionAttrMap(List<SAPMaterialVO.Zmdv> data) {
        return data.stream().collect(Collectors.toMap(
                o -> new MaterialKey(o.getMatnr(), MaterialCharCode.of(o.getCharCode())),
                o -> "CHAR".equalsIgnoreCase(o.getCharDataTyp()) ? o.getCharValChar() : o.getCharValNum(),
                (oldO, newO) -> StringUtils.hasLength(oldO) ? oldO : newO
        ));
    }

    private Map<String, String> toBusinessPartnerNameMap(List<SAPBusinessPartnerVO> businessPartners) {
        return businessPartners.stream().filter(o -> CompanyType.KOLMAR.getCode().equals(o.getCompanyCode()))
                .collect(Collectors.toMap(SAPBusinessPartnerVO::getPartnerNo, SAPBusinessPartnerVO::getNameOrg1, (oldO, newO) -> oldO));
    }

    public String getName(String materialCode) {
        return materialName.get(materialCode);
    }

    public String getBusinessPartnerName(String bpCode) {
        return businessPartnerName.get(bpCode);
    }

    public String get(String materialCode, MaterialCharCode charCode) {
        return additionAttr.get(new MaterialKey(materialCode, charCode));
    }

    public String get(String materialCode, String charCode) {
        return get(materialCode, MaterialCharCode.of(charCode));
    }


    @Getter
    @EqualsAndHashCode
    public static class MaterialKey {
        private String pitmCd;
        private MaterialCharCode charCode;

        public MaterialKey(String pitmCd, MaterialCharCode charCode) {
            this.pitmCd = pitmCd;
            this.charCode = charCode;
        }
    }
}
