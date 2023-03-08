package lims.api.ts.service.impl;

import lims.api.common.domain.FileKey;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.FileService;
import lims.api.integration.enums.TestStatusProcess;
import lims.api.integration.service.impl.IntegrationSender;
import lims.api.integration.vo.intergation.InterfaceSendVO;
import lims.api.ts.dao.TestResultCancelDao;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.enums.TestType;
import lims.api.ts.service.TestResultCancelService;
import lims.api.ts.vo.TestResultCancelVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestResultCancelServiceImpl implements TestResultCancelService {

    private final TestResultCancelDao dao;
    private final FileService fileService;
    private final IntegrationSender sender;

    @Override
    public List<TestResultCancelVO> findAll(TestResultCancelVO param) {
        return dao.findAll(param);
    }

    @Override
    public List<TestResultCancelVO> findResultItem(Integer ansIdx) {
        return dao.findResultItem(ansIdx);
    }

    @Override
    public void testCancel(List<TestResultCancelVO> list) {
        int result = 0;
        for(TestResultCancelVO row : list) {
            row.setAnsProcCd(TestProcess.TEST_CANCEL.getProcessCode());
            result += dao.testCancel(row);
            if(row.getAddCol2().equals("N")) {
                InterfaceSendVO.TestStatus data = setIFVO(row, TestStatusProcess.TEST_CANCEL.getValue());
                sender.sendTestStatus(data);
            }
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void hold(List<TestResultCancelVO> list) {
        int result = 0;
        for(TestResultCancelVO row : list) {
            result += dao.hold(row);
            InterfaceSendVO.TestStatus data = setIFVO(row, TestStatusProcess.TEST_HOLD.getValue());
            sender.sendTestStatus(data);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void holdOff(List<TestResultCancelVO> list) {
        int result = 0;
        for(TestResultCancelVO row : list) {
            result += dao.holdOff(row);
            InterfaceSendVO.TestStatus data = setIFVO(row, TestStatusProcess.TEST_PROCEEDING.getValue());
            sender.sendTestStatus(data);
        }

        if(list.size() != result) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public int savedFile(TestResultCancelVO param) {
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

    public InterfaceSendVO.TestStatus setIFVO(TestResultCancelVO item, int process){
        String reason = "";
        if(process == 2){
            reason = item.getHldCanlRea();
        }
        if(process == 3){
            reason = item.getHldRea();
        }
        if(process == 4){
            reason = item.getAnsCanlRea();
        }
        InterfaceSendVO.TestStatus data = InterfaceSendVO.TestStatus.builder()
                .lotNo(item.getLotNo())
                .splLotNo(item.getSplLotNo())
                .batchNo(item.getBatchNo())
                .holdReason(reason)
                .status(process)
                .ispReqNo(item.getIspReqNo())
                .phsOrderNo(item.getPhsOrderNo())
                .pdtOrderNo(item.getPdtOrderNo())
                .testType(TestType.of(item.getAnsTyp()))
                .build();
        return data;
    }
}