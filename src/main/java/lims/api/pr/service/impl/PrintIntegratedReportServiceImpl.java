package lims.api.pr.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.pr.dao.PrintIntegratedReportDao;
import lims.api.pr.service.PrintIntegratedReportService;
import lims.api.pr.vo.PrintIntegratedReportVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PrintIntegratedReportServiceImpl implements PrintIntegratedReportService {
    private final PrintIntegratedReportDao dao;

    @Override
    public List<PrintIntegratedReportVO> find(PrintIntegratedReportVO param) {
        return dao.find(param);
    }

    @Override
    public List<PrintIntegratedReportVO> findTestItem(PrintIntegratedReportVO param) {
        return dao.findTestItem(param);
    }

    @Override
    public List<PrintIntegratedReportVO> findReportHistory(PrintIntegratedReportVO param) {
        return dao.findReportHistory(param);
    }

    @Override
    public void save(PrintIntegratedReportVO param) {
        int result = 0;
        if(param.getArptSeq() == null){
            result = dao.create(param);
        }else{
            result = dao.update(param);
        }
        if (result == 0) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public PrintIntegratedReportVO findReportDetail(PrintIntegratedReportVO param) {
        return dao.findReportDetail(param);
    }
}
