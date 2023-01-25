package lims.api.integration.service;

import lims.api.integration.domain.eai.RevStateful;

public interface InterfaceErrorService {

    int record(Integer infoIdx, Exception e, String message);

    int record(Integer infoIdx, Exception e);

    int record(Exception e);

    int recordByPostProcess(Exception e, RevStateful rev, String message);

    int recordByPostProcess(Exception e, RevStateful rev);

}