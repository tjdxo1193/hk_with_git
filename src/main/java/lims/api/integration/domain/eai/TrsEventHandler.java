package lims.api.integration.domain.eai;

import lims.api.integration.model.InterfaceTrsResponse;

public interface TrsEventHandler<T> {

    void saveBeforeSend(T t);

    InterfaceTrsResponse send();

    void saveAfterSend(T t, InterfaceTrsResponse response);

    void saveOnError(T t);

}