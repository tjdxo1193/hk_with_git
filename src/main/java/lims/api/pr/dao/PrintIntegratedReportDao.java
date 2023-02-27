package lims.api.pr.dao;

import lims.api.pr.vo.PrintIntegratedReportVO;

import java.util.List;

public interface PrintIntegratedReportDao {
    List<PrintIntegratedReportVO> find(PrintIntegratedReportVO param);
}
