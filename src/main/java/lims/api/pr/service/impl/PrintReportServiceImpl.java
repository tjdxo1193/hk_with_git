package lims.api.pr.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.pr.dao.PrintReportDao;
import lims.api.pr.service.PrintReportService;
import lims.api.pr.vo.PrintReportVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrintReportServiceImpl implements PrintReportService {

    private final PrintReportDao dao;

    @Override
    public List<PrintReportVO> getTestReportList(PrintReportVO request) {
        return dao.getTestReportList(request);
    }

    @Override
    public List<PrintReportVO> getTestItmList(PrintReportVO request) {
        return dao.getTestItmList(request);
    }

    @Override
    public void updateRptInfo(PrintReportVO request) {
        int result = dao.updateReportInfo(request);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public String getReportPath(PrintReportVO param) {
        return dao.getReportPath(param);
    }
}
