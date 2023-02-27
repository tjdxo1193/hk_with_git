package lims.api.ts.service.impl;

import lims.api.common.domain.FileKey;
import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.FileService;
import lims.api.integration.enums.TestStatusProcess;
import lims.api.integration.service.impl.IntegrationSender;
import lims.api.integration.vo.intergation.InterfaceSendVO;
import lims.api.ts.dao.TestResultInputDao;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.enums.TestType;
import lims.api.ts.service.TestResultInputService;
import lims.api.ts.vo.TestResultInputVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestResultInputServiceImpl implements TestResultInputService {

    private final TestResultInputDao dao;
    private final FileService fileService;
    private final IntegrationSender sender;

    @Override
    public List<TestResultInputVO> findAll(TestResultInputVO param) {
        List<String> processList = new ArrayList<>();
        processList.add(0, TestProcess.TEST_RESULT_INPUT.getProcessCode());
        processList.add(1, TestProcess.TEST_REVIEW_REJECT.getProcessCode());
        param.setTestProcessList(processList);
        return dao.findAll(param);
    }

    @Override
    public List<TestResultInputVO> findResultItem(Integer ansIdx) {
        List<String> processList = new ArrayList<>();
        processList.add(0, TestProcess.TEST_RESULT_INPUT.getProcessCode());
        processList.add(1, TestProcess.TEST_REVIEW_REJECT.getProcessCode());
        processList.add(2, TestProcess.TEST_REVIEW.getProcessCode());

        TestResultInputVO param = new TestResultInputVO();
        param.setAnsIdx(ansIdx);
        param.setTestProcessList(processList);
        return dao.findResultItem(param);
    }

    @Override
    public List<TestResultInputVO> resultHistory(TestResultInputVO param) {
        param.setAnsProcCd(TestProcess.TEST_FINISH.getProcessCode());
        return dao.resultHistory(param);
    }

    @Override
    public void save(TestResultInputVO param) {
        int result = 0;
        int length = param.getEditedRowItems().size();
        if(length != 0) {
            for(TestResultInputVO row : param.getEditedRowItems()) {
                row.setRstProcCd(TestProcess.TEST_RESULT_INPUT_COMPLETED.getProcessCode());
                result += dao.save(row);
            }

            if(length != result) {
                throw new NoUpdatedDataException();
            }
        }

        if(param.getSytJdg().equals("")){
            int cnt = dao.getNullCount(param);
            if(cnt == 0){
                String judge = dao.getSytJdg(param);
                param.setSytJdg(judge);
            }
        }
        result = dao.updateTestInfo(param);
        dao.updateSytJdg(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void requestReview(TestResultInputVO param) {
        if(!param.getNonCfmRea().equals("") || !param.getPitmSpcc().equals("")){
            dao.updateTestInfo(param);
        }
        param.setAnsProcCd(TestProcess.TEST_REVIEW.getProcessCode());
        int result = dao.requestReview(param);
        if(result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void publishEvent(TestResultInputVO param) {
        int result = dao.publishEvent(param);

        if(result == 0) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public int savedFile(TestResultInputVO param) {
        if(param.getFileIdx() == 0) {
            FileKey fileKey = fileService.save(param.getAddedFiles());
            param.setFileIdx(fileKey.getFileIdx());
        }else {
            for (FileKey removedFileId : param.getRemovedFileIds()) {
                fileService.deleteFile(removedFileId);
            }
            fileService.save(param.getAddedFiles(), param.getFileIdx());
        }

        int result = 0;
        if(param.getRstSeq() == 0) {
            result += dao.savedRequestFile(param);
        }else {
            result += dao.savedItemFile(param);
        }

        if(result == 0) {
            throw new NoUpdatedDataException();
        }

        return param.getFileIdx();
    }

    @Override
    public void requestHold(TestResultInputVO param) {
        int result = dao.requestHold(param);

        // 보류 시 진행상태 전송
        InterfaceSendVO.TestStatus data = InterfaceSendVO.TestStatus.builder()
                .lotNo(param.getLotNo())
                .splLotNo(param.getSplLotNo())
                .batchNo(param.getBatchNo())
                .holdReason(param.getHldRea())
                .status(TestStatusProcess.TEST_HOLD.getValue())
                .ispReqNo(param.getIspReqNo())
                .phsOrderNo(param.getPhsOrderNo())
                .pdtOrderNo(param.getPdtOrderNo())
                .testType(TestType.of(param.getAnsTyp()))
                .build();
        sender.sendTestStatus(data);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}