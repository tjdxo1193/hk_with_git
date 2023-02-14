package lims.api.pr.service.impl;

import lims.api.common.vo.ApproveVO;
import lims.api.kp.enums.AddSampleProgress;
import lims.api.ms.enums.PItemType;
import lims.api.pr.dao.RePrintLabelRequestDao;
import lims.api.pr.enums.LabelPrtProcess;
import lims.api.pr.service.RePrintLabelRequestService;
import lims.api.pr.vo.RePrintLabelRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RePrintLabelRequestServiceImpl implements RePrintLabelRequestService {

    private final RePrintLabelRequestDao rePrintLabelRequestDao;

    // 라벨재출력요청 조회
    @Override
    public List<RePrintLabelRequestVO> findAll(RePrintLabelRequestVO param) {
        param.setPitmCdForDecode(PItemType.RAW_MATERIAL.getCode());
        param.setAddSmpProc(AddSampleProgress.APPROVE_ADD_SAMPLE.getCode());
        List<String> labelPrtProcList = Arrays.asList(LabelPrtProcess.LABEL_PRINT_PROCESSED.getCode(), LabelPrtProcess.RE_LABEL_PRINT_REJECT.getCode(), LabelPrtProcess.RE_LABEL_PRINT_APPROVED.getCode());
        param.setLabelPrtProcList(labelPrtProcList);
        return rePrintLabelRequestDao.findAll(param);
    }

    // 라벨재출력요청 모달 조회
    @Override
    public List<RePrintLabelRequestVO> findLabelList(RePrintLabelRequestVO param) {
        param.setPitmCdForDecode(PItemType.RAW_MATERIAL.getCode());
        param.setAddSmpProc(AddSampleProgress.APPROVE_ADD_SAMPLE.getCode());
        return rePrintLabelRequestDao.findLabelList(param);
    }

    // 라벨재출력요청
    @Override
    public int rePrintLabelRequest(RePrintLabelRequestVO param) {
        // 내일 할 일 ☆
        // 재출력 요청, 반려, 취소 프로세스 만들기
        // 테이블 설계 관련 질문, 부장님께 확인받기

        ApproveVO approveVO = new ApproveVO();
        RePrintLabelRequestVO asdf = new RePrintLabelRequestVO();
        return 0;
    }

    // 라벨재출력취소
    @Override
    public int rePrintLabelCancel(RePrintLabelRequestVO param) {
        RePrintLabelRequestVO qsSmpLabelPrt = new RePrintLabelRequestVO();
        qsSmpLabelPrt.setLabelPrtIdx(param.getLabelPrtIdx());
        qsSmpLabelPrt.setPrtSeq(param.getPrtSeq());
        qsSmpLabelPrt.setLabelPrtProc(LabelPrtProcess.LABEL_PRINT_CANCEL.getCode());

        return rePrintLabelRequestDao.rePrintLabelCancel(qsSmpLabelPrt);

    }

}
