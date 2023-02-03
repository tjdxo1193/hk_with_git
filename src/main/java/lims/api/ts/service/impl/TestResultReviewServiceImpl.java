package lims.api.ts.service.impl;

import lims.api.common.domain.FileKey;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.service.FileService;
import lims.api.common.service.UserService;
import lims.api.integration.enums.TestStatusProcess;
import lims.api.integration.service.impl.IntegrationSender;
import lims.api.integration.vo.intergation.InterfaceSendVO;
import lims.api.ts.dao.TestResultReviewDao;
import lims.api.ts.enums.TestProcess;
import lims.api.ts.service.TestResultReviewService;
import lims.api.ts.vo.TestResultReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestResultReviewServiceImpl implements TestResultReviewService {

    private final TestResultReviewDao dao;
    private final ApproveService approveService;
    private final FileService fileService;
    private final UserService userService;
    private final IntegrationSender sender;

    @Override
    public List<TestResultReviewVO> findAll(TestResultReviewVO param) {
        List<String> processList = new ArrayList<>();
        processList.add(0, TestProcess.TEST_REVIEW.getProcessCode());
        processList.add(1, TestProcess.TEST_APPROVAL_REJECT.getProcessCode());
        param.setTestProcessList(processList);
        param.setWithDelegateUserIds(userService.getDelegateAssignUserIdsWithMe(param.getRevwUid()));
        return dao.findAll(param);
    }

    @Override
    public List<TestResultReviewVO> findResultItem(Integer ansIdx) {
        List<String> processList = new ArrayList<>();
        processList.add(0, TestProcess.TEST_REVIEW.getProcessCode());
        processList.add(1, TestProcess.TEST_APPROVAL_REJECT.getProcessCode());

        TestResultReviewVO param = new TestResultReviewVO();
        param.setAnsIdx(ansIdx);
        param.setRstProcCd(TestProcess.TEST_RESULT_INPUT_COMPLETED.getProcessCode());
        param.setTestProcessList(processList);
        return dao.findResultItem(param);
    }

    @Override
    public void completedReview(TestResultReviewVO param) {
        int rstAprReqIdx = approveService.requestApprove(param.getApproveInfo());
        param.setAnsProcCd(TestProcess.TEST_APPROVED.getProcessCode());
        param.setRstAprReqIdx(rstAprReqIdx);
        int result = dao.completedReview(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void requestHold(TestResultReviewVO param) {
        int result = dao.requestHold(param);

        // 보류 시 진행상태 전송
        InterfaceSendVO.TestStatus data = InterfaceSendVO.TestStatus.builder()
                .lotNo(param.getLotNo())
                .batchNo(param.getBatchNo())
                .holdReason(param.getHldRea())
                .status(TestStatusProcess.TEST_HOLD.getValue())
                .ispReqNo(param.getIspReqNo())
                .phsOrderNo(param.getPhsOrderNo())
                .pdtOrderNo(param.getPdtOrderNo())
                .build();
        sender.sendTestStatus(data);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void requestRejection(TestResultReviewVO param) {
        param.setAnsProcCd(TestProcess.TEST_REVIEW_REJECT.getProcessCode());
        int result = dao.requestRejection(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public int savedFile(TestResultReviewVO param) {
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
}
