package lims.api.integration.domain.rfc;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import lims.api.integration.enums.CompanyType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssetsRFC implements RFC {

    @Override
    public String getFunctionName() {
        return "ZFI_IF_O_ASSET_MASTER";
    }

    @Override
    public void setSearchParameter(JCoFunction function, RFCParam param) {
        JCoParameterList input = function.getImportParameterList();
        input.setValue("IV_BUKRS", CompanyType.KOLMAR.getCode());

        // TODO 파라미터 설정

    }

    @Override
    public String getResultJson(JCoFunction function) {
        JCoStructure structure = function.getExportParameterList().getStructure("ES_RETURN");

        System.out.println(structure.toJSON());
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