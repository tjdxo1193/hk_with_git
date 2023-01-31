package lims.api.ts.service.impl;

import lims.api.ts.dao.TestIFModalDao;
import lims.api.ts.service.TestIFModalService;
import lims.api.ts.vo.TestIFModalVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestIFModalServiceImpl implements TestIFModalService {

    private final TestIFModalDao dao;

    @Override
    public List<TestIFModalVO> getSrmFinalOrderList(TestIFModalVO request) {
        return dao.getSrmFinalOrderList(request);
    }

    @Override
    public List<TestIFModalVO> getMesFinalOrderList(TestIFModalVO request) {
        return dao.getMesFinalOrderList(request);
    }

    @Override
    public List<TestIFModalVO> getPrvRcrReportList(TestIFModalVO request) throws IOException {
        List<TestIFModalVO> result = dao.getPrvRcrReportList(request);
        for(TestIFModalVO row : result){
            byte[] file = row.getSrc();
            InputStream is = new ByteArrayInputStream(file);
            String mimeType = URLConnection.guessContentTypeFromStream(is);
            row.setSize((long) file.length);
            row.setType(mimeType);
        }
        return result;
    }

    @Override
    public List<TestIFModalVO> getSrmReportList(TestIFModalVO request) throws IOException {
        List<TestIFModalVO> result = dao.getSrmReportList(request);
        for(TestIFModalVO row : result){
            byte[] file = row.getSrc();
            InputStream is = new ByteArrayInputStream(file);
            String mimeType = URLConnection.guessContentTypeFromStream(is);
            row.setSize((long) file.length);
            row.setType(mimeType);
        }
        return result;
    }

    @Override
    public List<TestIFModalVO> getPackingSpecList(TestIFModalVO request) throws IOException {
        List<TestIFModalVO> result = dao.getPackingSpecList(request);
        for(TestIFModalVO row : result){
            byte[] file = row.getSrc();
            InputStream is = new ByteArrayInputStream(file);
            String mimeType = URLConnection.guessContentTypeFromStream(is);
            row.setSize((long) file.length);
            row.setType(mimeType);
        }
        return result;
    }

    @Override
    public List<TestIFModalVO> getInpPerformanceList(TestIFModalVO request) {
        return dao.getInpPerformanceList(request);
    }
}
