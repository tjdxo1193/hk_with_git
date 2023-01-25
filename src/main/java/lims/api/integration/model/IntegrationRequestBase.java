package lims.api.integration.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IntegrationRequestBase<T> {

    private List<T> dataList;

}