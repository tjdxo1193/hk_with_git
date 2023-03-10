package lims.api.pr.service;

import lims.api.pr.vo.PrintIntegratedReportVO;

import java.util.List;

public interface PrintIntegratedReportService {
    List<PrintIntegratedReportVO> find(PrintIntegratedReportVO param);

    List<PrintIntegratedReportVO> findTestItem(PrintIntegratedReportVO param);

    List<PrintIntegratedReportVO> findReportHistory(PrintIntegratedReportVO param);

    void save(PrintIntegratedReportVO param);

    PrintIntegratedReportVO findReportDetail(PrintIntegratedReportVO param);
}
