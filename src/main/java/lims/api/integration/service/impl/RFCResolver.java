package lims.api.integration.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.sap.conn.jco.*;
import lims.api.integration.domain.rfc.RFC;
import lims.api.integration.domain.rfc.RFCParam;
import lims.api.integration.properties.JcoProperties;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RFCResolver {

    private final JcoProperties jcoProperties;

    public RFCExecutor getExecutor(RFC rfc) {
        return new RFCExecutor(rfc);
    }

    private String getDestinationName() {
        return jcoProperties.getJcoDestinationFileName();
    }


    public class RFCExecutor {

        private final RFC rfc;

        public RFCExecutor(RFC rfc) {
            this.rfc = rfc;
        }

        public <T> T executeOne(RFCParam param, Class<T> resultType) {
            return new Gson().fromJson(executeRFC(param), resultType);
        }

        public <T> List<T> execute(RFCParam param, Class<T[]> resultType) {
            T[] results = new Gson().fromJson(executeRFC(param), resultType);
            return Arrays.asList(results);
        }

        private String executeRFC(RFCParam param) {
            try {
                JCoDestination destination = getDestination();
                JCoFunction function = getFunction(destination);
                log.info("{}", function.getFunctionTemplate()); // RFC 테이블 구성을 보여줍니다.
                rfc.setSearchParameter(function, param);

                try {
                    // RFC 통신.
                    function.execute(destination);
                } catch (Exception e) {
                    log.error("Throw Exception during RFC. function name: {}", function.getName());
                    throw e;
                }

                String json = rfc.getResultJson(function);
                assertJsonFormat(json);
                return json;
            } catch (Exception e) {
                log.error("[{}] Failed RFC. error message: {}", ThreadUtil.getCurrentMethodName(), e.getMessage());
                throw new RuntimeException(e.getCause());
            }
        }

        private JCoDestination getDestination() throws JCoException {
            return JCoDestinationManager.getDestination(getDestinationName());
        }

        // RFC할 Function 가져오기
        private JCoFunction getFunction(JCoDestination destination) throws JCoException {
            JCoRepository repository = destination.getRepository();
            return repository.getFunction(rfc.getFunctionName());
        }

        private void assertJsonFormat(String json) {
            try {
                JsonParser.parseString(json);
            } catch (JsonSyntaxException e) {
                throw new RuntimeException("RFC result format is not JSON string.");
            }
        }

    }

}