package lims.api.integration.domain.rfc;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import lims.api.integration.enums.CompanyType;
import lims.api.integration.enums.RfcInterface;

public class BusinessPartnerRFC implements RFC {

    @Override
    public String getFunctionName() {
        return RfcInterface.BUSINESS_PARTNER.getFunctionName();
    }

    @Override
    public void setSearchParameter(JCoFunction function, RFCParam<? extends Enum<?>, String> param) {
        JCoStructure input = function.getImportParameterList().getStructure("IS_INPUT");
        input.getField("BUKRS").setValue(CompanyType.KOLMAR.getCode());

        JCoStructure condition = function.getImportParameterList().getStructure("IS_BP_CHECK");
        param.forEach((enums, value) -> condition.getField(enums.name()).setValue(value));
    }

    @Override
    public String getResultJson(JCoFunction function) {
        return function.getTableParameterList().getTable("ET_BP_GENERAL").toJSON();
    }

}