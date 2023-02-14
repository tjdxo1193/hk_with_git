package lims.api.integration.domain.rfc;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public interface RFC {

    String getFunctionName();

    /**
     * 검색 파라미터를 콜마 SAP RFC에서 받는 형태로 변환함.
     * 검색 파라미터를 지정하지 않으면 "NO DATA" triggered 예외가 발생.
     * 따라서 파라미터는 최소 1개 이상 필수적으로 존재해야 함.
     */
    void setSearchParameter(JCoFunction function, RFCParam param);

    // RFC 후 function 객체에 저장된 결과값을 JSON으로 꺼냄.
    String getResultJson(JCoFunction function);

}