package lims.api.util.httpClient.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import lims.api.util.StringUtil;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpEntityFactory {

    private final HttpHeadersFactory headersFactory;

    public HttpEntity<String> json(Object body) {
        return new HttpEntity<>(toJson(body), headersFactory.json());
    }

    public HttpEntity<String> json() {
        return json(null);
    }

    public HttpEntity<MultiValueMap<String, HttpEntity<?>>> multipartFile(Object body, String fileName, byte[] fileBytes) {
        String encodedFileName = StringUtil.encodeUTF8(fileName);

        MultiValueMap<String, HttpEntity<?>> multipartBody = new LinkedMultiValueMap<>();

        /**
         * @implNote EAI측에서 multipart를 전송할 때,
         *           바이너리를 제외한 데이터는 마지막 바운더리에 넣고 name을 mainPayload로 해야 파싱이 가능하다고 함.
         */
        ByteArrayResource resource = new ByteArrayResource(fileBytes);
        multipartBody.add(encodedFileName, new HttpEntity<>(resource, headersFactory.getFileHeaders(encodedFileName)));
        multipartBody.add("mainPayload", json(body));

        return new HttpEntity<>(multipartBody, headersFactory.multipart());
    }

    private String toJson(Object arg) {
        if (arg == null) {
            return "{}";
        }

        String json;

        try {
            json = new Gson().toJson(arg);
        } catch (Exception e) {
            log.error("[{}] Failed convert to json. arg: {}", ThreadUtil.getCurrentMethodName(), arg.getClass().getName());
            throw new IllegalArgumentException("Failed convert parameter to json.");
        }
        return json;
    }
}