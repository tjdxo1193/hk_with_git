package lims.api.integration.domain.rfc;

import com.sap.conn.jco.*;

public class BusinessPartnerRFC implements RFC {

    @Override
    public String getFunctionName() {
        return "ZFI_IF_I_BP_CHECK";
    }

    @Override
    public void setSearchParameter(JCoFunction function, RFCParam param) {
        JCoStructure input = function.getImportParameterList().getStructure("IS_INPUT");
        input.getField("LEGACYID").setValue("LIMS");
        input.getField("BUKRS").setValue("1000");

        JCoStructure condition = function.getImportParameterList().getStructure("IS_BP_CHECK");
        param.forEach((name, value) -> condition.getField(name).setValue(value));
    }

    @Override
    public JCoTable getResultJson(JCoFunction function) {
        JCoTable generalTable = function.getTableParameterList().getTable("ET_BP_GENERAL");
        JCoTable mmTable = function.getTableParameterList().getTable("ET_BP_MM");

        /**
         * TODO
         *  generalTable에서 mmTable 항목들의 이름을 가져와서 setting 한 후 리턴
         */

        JCoRecordFieldIterator iterator = mmTable.getRecordFieldIterator();
//        while (iterator.hasNextField()) {
//            JCoRecordField field = iterator.nextRecordField();
//            System.out.println(field.getValue());
//        }

        return function.getTableParameterList().getTable("ET_BP_GENERAL");
    }

}