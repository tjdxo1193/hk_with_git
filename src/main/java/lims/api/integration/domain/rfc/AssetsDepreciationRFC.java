package lims.api.integration.domain.rfc;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public class AssetsDepreciationRFC implements RFC {

    @Override
    public String getFunctionName() {
        return "ZFI_IF_O_ASSET_DEPR";
    }

    @Override
    public void setSearchParameter(JCoFunction function, RFCParam param) {
        JCoParameterList list = function.getImportParameterList();
        list.setValue("I_BUKRS", "1000");
        // TODO 파라미터로
//        list.setValue("I_ANLKL", );
//        list.setValue("I_BZDAT", "20221231");
    }

    @Override
    public String getResultJson(JCoFunction function) {
        String json = function.getExportParameterList().getStructure("ES_RETURN").toJSON();
        System.out.println(json);
        return json;
    }
}