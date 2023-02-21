package lims.api.util.httpClient.executor;

import org.springframework.http.ResponseEntity;

public interface HttpRestExecutor<T> {

    ResponseEntity<T> execute();

}