package lims.api.integration.domain.rfc;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import lims.api.integration.enums.CompanyType;
import lims.api.integration.enums.rfc.RFCAssetsDepreciation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssetsDepreciationRFC implements RFC {

    @Override
    public String getFunctionName() {
        return "ZFI_IF_O_ASSET_DEPR";
    }

    @Override
    public void setSearchParameter(JCoFunction function, RFCParam param) {
        JCoParameterList input = function.getImportParameterList();
        input.setValue("I_BUKRS", CompanyType.KOLMAR.getCode());
        input.setValue("I_BZDAT", "20221231");

        String anlkl = RFCAssetsDepreciation.ANLKL.name();
        if (param.containsKey(anlkl)) {
            throw new RuntimeException("No exists required parameter. RFCAssetsDepreciation.ANLKL.");
        }

        input.setValue("I_ANLKL", param.get(anlkl));
//        list.setValue("I_BZDAT", "20221231");
    }

    @Override
    public String getResultJson(JCoFunction function) {
        JCoStructure structure = function.getExportParameterList().getStructure("ES_RETURN");

        if (isError(structure)) {
            log.error("RFC error. {}", structure.toJSON());
            return null;
        }
        return function.getTableParameterList().getTable("ET_ASSET_OUT").toJSON();
    }

    private boolean isError(JCoStructure structure) {
        String v = String.valueOf(structure.getValue("TYPE"));
        return "E".equals(v);
    }
}