package lims.api.integration.domain.rfc;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class CommonCodeRFC implements RFC {

    @Override
    public String getFunctionName() {
        return "ZMM_005_COMMONCODE_RFC";
    }

    @Override
    public void setSearchParameter(JCoFunction function, RFCParam param) {
        JCoTable table = function.getTableParameterList().getTable("IT_SEARCH_CONDITION");
        table.appendRow();
        param.forEach((name, value) -> {
            table.getField("CD_FIELD").setValue(name);
            table.getField("KEYFIELD1").setValue(value);
        });
    }

    @Override
    public String getResultJson(JCoFunction function) {
        return function.getTableParameterList().getTable("ET_CODELIST").toJSON();
    }
}