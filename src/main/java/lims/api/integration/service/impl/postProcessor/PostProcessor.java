package lims.api.integration.service.impl.postProcessor;

import lims.api.integration.domain.eai.RevStateful;

public interface PostProcessor {

    void execute(RevStateful rev);

}