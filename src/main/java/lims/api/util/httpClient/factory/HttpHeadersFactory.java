package lims.api.util.httpClient.factory;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;

@Component
@PropertySource("classpath:interface-system/${spring.profiles.active}.properties")
public class HttpHeadersFactory {

    @Value("${interface.eai.username}")
    private String EAI_USERNAME;

    @Value("${interface.eai.password}")
    private String EAI_PASSWORD;

    public HttpHeaders json() {
        MediaType applicationJsonUtf8 = new MediaType("application", "json", StandardCharsets.UTF_8);
        return createHeaders(applicationJsonUtf8);
    }

    public HttpHeaders multipart() {
        return createHeaders(MediaType.MULTIPART_FORM_DATA);
    }

    private HttpHeaders createHeaders(MediaType type) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", createBasicAuthorization());
        headers.setContentType(type);
        headers.set("eai", "true"); // for qms
        return headers;
    }

    private String createBasicAuthorization() {
        String account = EAI_USERNAME + ":" + EAI_PASSWORD;
        byte[] base64Bytes = Base64.encodeBase64(account.getBytes());
        return "Basic" + " " + new String(base64Bytes);
    }

    public MultiValueMap<String, String> getFileHeaders(String fileName) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Disposition", String.format("form-data; name=%s; filename=%s", fileName, fileName));
        return headers;
    }

}