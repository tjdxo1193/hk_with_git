package lims.api.common.service.impl;

import lims.api.auth.service.impl.HttpHelper;
import lims.api.common.dao.ErrorDao;
import lims.api.common.model.ErrorResponse;
import lims.api.common.vo.ErrorVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class ErrorService {

    private final ErrorDao errorDao;

    public ErrorService record(Exception e) {
        saveErrorLog(e);
        return this;
    }

    public ResponseEntity<ErrorResponse> response(String message, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ErrorResponse(message), httpStatus);
    }

    private synchronized void saveErrorLog(Exception e) {
        Long errorlogId = errorDao.nextIdx();
        ErrorVO param = ErrorVO.of(errorlogId, getRequest(), e);
        errorDao.create(param);
    }

    private HttpServletRequest getRequest() {
        return HttpHelper.getHttpServletRequest();
    }

}