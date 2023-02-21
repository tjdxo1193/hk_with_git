package lims.api.integration.service.impl;

import com.google.gson.*;
import com.sap.conn.jco.*;
import lims.api.integration.domain.rfc.RFC;
import lims.api.integration.domain.rfc.RFCParam;
import lims.api.integration.properties.JcoProperties;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

        private final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CASE_WITH_UNDERSCORES).create();

        public RFCExecutor(RFC rfc) {
            this.rfc = rfc;
        }

        public <T> List<T> execute(RFCParam<? extends Enum<?>, String> param, Class<T[]> resultType) {
            String json = Optional.ofNullable(executeRFC(param)).orElse("[]");
            T[] results = gson.fromJson(json, resultType);
            return Arrays.asList(results);
        }

        private String executeRFC(RFCParam<? extends Enum<?>, String> param) {
            try {
                JCoDestination destination = getDestination();
                JCoFunction function = getRFCFunction(destination);
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
                log.info("Result of cal RFC. \n {}", json);
                return json;
            } catch (Exception e) {
                log.error("[{}] Failed RFC. error message: {}", ThreadUtil.getCurrentMethodName(), e.getMessage());
                throw new RuntimeException(e);
            }
        }

        private JCoDestination getDestination() throws JCoException {
            return JCoDestinationManager.getDestination(getDestinationName());
        }

        // RFC할 Function 가져오기
        private JCoFunction getRFCFunction(JCoDestination destination) throws JCoException {
            JCoRepository repository = destination.getRepository();
            return repository.getFunction(rfc.getFunctionName());
        }

        private void assertJsonFormat(String json) {
            if (StringUtils.isEmpty(json)) {
                return;
            }
            try {
                JsonParser.parseString(json);
            } catch (JsonSyntaxException e) {
                throw new RuntimeException("RFC result format is not JSON string.");
            }
        }

    }

}