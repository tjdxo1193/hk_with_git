package lims.api.pr.dao;

import lims.api.pr.vo.PrintIntegratedReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PrintIntegratedReportDao {
    List<PrintIntegratedReportVO> find(PrintIntegratedReportVO param);

    List<PrintIntegratedReportVO> findTestItem(PrintIntegratedReportVO param);

    List<PrintIntegratedReportVO> findReportHistory(PrintIntegratedReportVO param);

    int create(PrintIntegratedReportVO param);

    PrintIntegratedReportVO findReportDetail(PrintIntegratedReportVO param);
}
