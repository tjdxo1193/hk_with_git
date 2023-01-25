package lims.api.integration.service.impl;

import lims.api.integration.dao.InterfaceErrorDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.service.InterfaceErrorService;
import lims.api.integration.vo.InterfaceErrorVO;
import lims.api.integration.vo.InterfacePostProcessErrorVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterfaceErrorServiceImpl implements InterfaceErrorService {

    private final InterfaceErrorDao errorDao;

    @Override
    public synchronized int record(Integer infoIdx, Exception e, String message) {
        int id = errorDao.nextId();

        InterfaceErrorVO vo = InterfaceErrorVO.builder()
                .errorlogId(id)
                .ifInfoIdx(infoIdx)
                .exception(e.getClass().getSimpleName())
                .message(getErrorMessage(e, message))
                .stackTrace(getErrorStackTrace(e))
                .build();
        errorDao.createLog(vo);
        return id;
    }

    public int record(Integer infoIdx, Exception e) {
        return record(infoIdx, e, e.getMessage());
    }

    public int record(Exception e) {
        return record(null, e, e.getMessage());
    }


    @Override
    public synchronized int recordByPostProcess(Exception e, RevStateful rev, String message) {
        int id = errorDao.nextIdOfPostProcess();

        InterfacePostProcessErrorVO vo = InterfacePostProcessErrorVO.builder()
                .errorlogId(id)
                .ifInfoIdx(rev.getIfInfoIdx())
                .degree(rev.getDegree())
                .exception(e.getClass().getSimpleName())
                .message(getErrorMessage(e, message))
                .stackTrace(getErrorStackTrace(e))
                .build();
        errorDao.createLogOfPostProcess(vo);
        return id;
    }

    public int recordByPostProcess(Exception e, RevStateful rev) {
        return recordByPostProcess(e, rev, e.getMessage());
    };

    private String getErrorMessage(Exception e, String defaultMessage) {
        String message = e.getMessage();
        if (message == null) {
            return defaultMessage;
        }
        return message.length() > 3000 ? message.substring(0, 3000) : message;
    }

    private String getErrorStackTrace(Exception e) {
        String stackTrace = ExceptionUtils.getStackTrace(e);
        return stackTrace.length() > 3000 ? stackTrace.substring(0, 3000) : stackTrace;
    }
}