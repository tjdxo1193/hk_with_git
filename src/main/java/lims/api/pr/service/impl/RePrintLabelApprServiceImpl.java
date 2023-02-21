package lims.api.pr.service.impl;

import lims.api.common.service.ApproveService;
import lims.api.kp.enums.AddSampleProgress;
import lims.api.ms.enums.PItemType;
import lims.api.pr.dao.RePrintLabelApprDao;
import lims.api.pr.enums.LabelPrtProcess;
import lims.api.pr.service.RePrintLabelApprService;
import lims.api.pr.vo.RePrintLabelApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RePrintLabelApprServiceImpl implements RePrintLabelApprService {

    private final RePrintLabelApprDao rePrintLabelApprDao;
    private final ApproveService approveService;

    // 라벨재출력승인 조회
    @Override
    public List<RePrintLabelApprVO> findAll(RePrintLabelApprVO param) {
        param.setPitmCdForDecode(PItemType.RAW_MATERIAL.getCode());
        param.setAddSmpProc(AddSampleProgress.APPROVE_ADD_SAMPLE.getCode());
        List<String> labelPrtProcList = Arrays.asList(LabelPrtProcess.RE_LABEL_PRINT_REQUEST.getCode());
        param.setLabelPrtProcList(labelPrtProcList);
        return rePrintLabelApprDao.findAll(param);
    }

    // 라벨재출력승인
    @Override
    @Transactional
    public int approve(RePrintLabelApprVO param) {
        // SY_APR_INFO
        Integer labelRePrtAprIdx = param.getLabelRePrtAprIdx();
        approveService.approve(labelRePrtAprIdx);

        // QS_SMP_LABEL_PRT
        RePrintLabelApprVO qsSmpLabelPrt = new RePrintLabelApprVO();
        qsSmpLabelPrt.setLabelPrtIdx(param.getLabelPrtIdx());
        qsSmpLabelPrt.setPrtSeq(param.getPrtSeq());
        qsSmpLabelPrt.setLabelPrtProc(LabelPrtProcess.RE_LABEL_PRINT_APPROVED.getCode());

        return rePrintLabelApprDao.approve(qsSmpLabelPrt);
    }

    // 라벨재출력 반려
    @Override
    public int reject(RePrintLabelApprVO param) {
        RePrintLabelApprVO qsSmpLabelPrt = new RePrintLabelApprVO();

        qsSmpLabelPrt.setLabelPrtIdx(param.getLabelPrtIdx());
        qsSmpLabelPrt.setPrtSeq(param.getPrtSeq());
        qsSmpLabelPrt.setLabelPrtProc(LabelPrtProcess.RE_LABEL_PRINT_REJECT.getCode());
        qsSmpLabelPrt.setLabelRePrtAprIdx(param.getLabelRePrtAprIdx());
        qsSmpLabelPrt.setLabelRePrtRjtUid(param.getLoginUserUid());
        qsSmpLabelPrt.setLabelRePrtRjtRea(param.getRjtRea());

        return rePrintLabelApprDao.reject(qsSmpLabelPrt);
    }
}
