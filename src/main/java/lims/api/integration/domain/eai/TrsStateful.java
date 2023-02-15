package lims.api.integration.domain.eai;

import com.fasterxml.jackson.annotation.JsonInclude;
import lims.api.integration.enums.InterfaceResponseStatus;
import lims.api.integration.enums.InterfaceTrsStatus;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.exception.CloneFailedException;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class TrsStateful implements Cloneable {

    Integer idx;
    Integer degree;
    Integer errorlogId;
    Integer ifInfoIdx;
    InterfaceTrsStatus trsStatus;
    InterfaceResponseStatus responseStatus;
    String responseMessage;

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch(Exception e) {
            throw new CloneFailedException(e);
        }
    }

    public String getTrsStatus() {
        return trsStatus == null ? null : trsStatus.getValue();
    }
}