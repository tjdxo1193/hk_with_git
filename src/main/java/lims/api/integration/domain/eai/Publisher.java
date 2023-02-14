package lims.api.integration.domain.eai;

import lims.api.integration.model.InterfaceTrsResponse;
import lims.api.util.httpClient.HttpRestInfo;
import lims.api.util.httpClient.HttpRestTemplate;
import lims.api.util.httpClient.executor.HttpGetExecutor;
import lims.api.util.httpClient.executor.HttpPostExecutor;
import lims.api.util.httpClient.factory.HttpEntityFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

@Slf4j
public class Publisher {

    private final HttpRestTemplate template;
    private final InterfaceSystem system;
    private final HttpEntityFactory entityFactory;

    public Publisher(InterfaceSystem system, HttpEntityFactory entityFactory) {
        this.system = system;
        this.template = new HttpRestTemplate();
        this.entityFactory = entityFactory;
    }

    public <T> T post(String path, Class<T> responseType, Object bodyParameter) {
        HttpRestInfo<T> info = createHttpInfo(path, responseType);
        HttpEntity<String> entity = entityFactory.json(bodyParameter);
        return template.request(new HttpPostExecutor<>(info, entity));
    }

    public <T> T getEAI(String path, Class<T> responseType, Object... uriVariables) {
        HttpRestInfo<T> info = createEAIHttpInfo(path, responseType, uriVariables);
        HttpEntity<String> entity = entityFactory.json();
        return template.request(new HttpGetExecutor<>(info, entity));
    }

    public <T> T postEAI(String path, Class<T> responseType, Object bodyParameter) {
        HttpRestInfo<T> info = createEAIHttpInfo(path, responseType);
        HttpEntity<String> entity = entityFactory.json(bodyParameter);
        return template.request(new HttpPostExecutor<>(info, entity));
    }

    public InterfaceTrsResponse postEAI(String path, Object bodyParameter) {
        return postEAI(path, InterfaceTrsResponse.class, bodyParameter);
    }

    public <T> T postEAI(String path, Object bodyParameter, Class<T> responseType) {
        return postEAI(path, responseType, bodyParameter);
    }

    public <T> T postEAIForFile(String path, Class<T> responseType, Object bodyParameter, String fileName, byte[] fileBytes) {
        HttpRestInfo<T> info = createEAIHttpInfo(path, responseType);
        HttpEntity<MultiValueMap<String, HttpEntity<?>>> entity = entityFactory.multipartFile(bodyParameter, fileName, fileBytes);
        return template.request(new HttpPostExecutor<>(info, entity));
    }

    public InterfaceTrsResponse postEAIForFile(String path, Object bodyParameter, String fileName, byte[] fileBytes) {
        return postEAIForFile(path, InterfaceTrsResponse.class, bodyParameter, fileName, fileBytes);
    }

    private <T> HttpRestInfo<T> createHttpInfo(String path, Class<T> responseType, Object... uriVariables) {
        return HttpRestInfo.<T>builder()
                .protocol(system.getProtocol())
                .host(system.getHost())
                .path(path)
                .responseType(responseType)
                .uriVariables(uriVariables)
                .build();
    }

    private <T> HttpRestInfo<T> createEAIHttpInfo(String path, Class<T> responseType, Object... uriVariables) {
        return HttpRestInfo.<T>builder()
                .protocol(system.getEAIProtocol())
                .host(system.getEAIHost())
                .path(path)
                .responseType(responseType)
                .uriVariables(uriVariables)
                .build();
    }

}