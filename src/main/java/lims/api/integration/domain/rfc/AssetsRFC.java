package lims.api.integration.domain.rfc;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import lims.api.integration.enums.CompanyType;
import lims.api.integration.enums.RfcInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssetsRFC implements RFC {

    @Override
    public String getFunctionName() {
        return RfcInterface.ASSETS.getFunctionName();
    }

    @Override
    public void setSearchParameter(JCoFunction function, RFCParam<? extends Enum<?>, String> param) {
        JCoParameterList input = function.getImportParameterList();
        input.getField("IV_BUKRS").setValue(CompanyType.KOLMAR.getCode());
        param.forEach((enums, value) -> input.getField(enums.name()).setValue(value));
    }

    @Override
    public String getResultJson(JCoFunction function) {
        JCoStructure structure = function.getExportParameterList().getStructure("ES_RETURN");
        log.info("[AssetsRFC] Result of call RFC. {}", structure.toJSON());
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