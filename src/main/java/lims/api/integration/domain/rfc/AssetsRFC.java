package lims.api.integration.domain.rfc;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class AssetsRFC implements RFC {

    @Override
    public String getFunctionName() {
        return "";
    }

    @Override
    public void setSearchParameter(JCoFunction function, RFCParam param) {

    }

    @Override
    public String getResultJson(JCoFunction function) {
        return null;
    }
}