package lims.api.ts.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.integration.enums.TestStatusProcess;
import lims.api.integration.service.impl.IntegrationSender;
import lims.api.integration.vo.intergation.InterfaceSendVO;
import lims.api.ts.dao.TestReceiptDao;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.service.TestReceiptService;
import lims.api.ts.vo.TestReceiptVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestReceiptServiceImpl implements TestReceiptService {

    private final TestReceiptDao dao;
    private final IntegrationSender sender;

    @Override
    public List<TestReceiptVO> getTestReceiptList(TestReceiptVO request) {
        request.setAnsProcCd(TestProcess.TEST_RECEIPT.getProcessCode());
        return dao.getTestReceiptList(request);
    }

    @Override
    public List<TestReceiptVO> getTestAitm(TestReceiptVO request) {
        return dao.getTestAitm(request);
    }

    @Override
    public void receipt(List<TestReceiptVO> request) {
        int result = 0;
        for (TestReceiptVO item : request) {
            item.setAnsProcCd(TestProcess.TEST_COLLECTION.getProcessCode());
            if(item.getAnsIdx() == 0){
                int ansIdx = dao.getAnsIdx(item.getPlntCd());
                item.setAnsIdx(ansIdx);
                result += dao.insertTestProc(item); //시험 프로세스
            }else{
                result += dao.save(item);
            }
            dao.insertTestInfo(item); //시험정보
            dao.insertTestRst(item); //시험결과

            //진행상태 데이터 보내기
            InterfaceSendVO.TestStatus data = InterfaceSendVO.TestStatus.builder()
                    .lotNo(item.getLotNo())
                    .splLotNo(item.getSplLotNo())
                    .batchNo(item.getBatchNo())
                    .status(TestStatusProcess.TEST_PROCEEDING.getValue())
                    .ispReqNo(item.getIspReqNo())
                    .phsOrderNo(item.getPhsOrderNo())
                    .pdtOrderNo(item.getPdtOrderNo())
                    .build();
            sender.sendTestStatus(data);
        }
        if (result != request.size()) {
            throw new NoUpdatedDataException();
        }

    }

    @Override
    public List<TestReceiptVO> getNonconformityTestList(TestReceiptVO request) {
        return dao.getNonconformityTestList(request);
    }
}
