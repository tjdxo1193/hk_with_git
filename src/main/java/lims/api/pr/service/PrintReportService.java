package lims.api.pr.service;

import lims.api.pr.vo.PrintReportVO;

import java.util.List;

public interface PrintReportService {
    List<PrintReportVO> getTestReportList(PrintReportVO request);
    List<PrintReportVO> getTestItmList(PrintReportVO request);
    void updateRptInfo(PrintReportVO request);
}
