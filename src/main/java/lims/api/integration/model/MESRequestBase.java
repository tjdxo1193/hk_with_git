package lims.api.integration.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MESRequestBase<T> extends IntegrationRequestBase<T> {
}