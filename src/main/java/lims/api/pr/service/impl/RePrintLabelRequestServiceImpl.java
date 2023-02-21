package lims.api.pr.service.impl;

import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.ms.enums.PItemType;
import lims.api.pr.dao.RePrintLabelRequestDao;
import lims.api.pr.enums.LabelPrtProcess;
import lims.api.pr.service.RePrintLabelRequestService;
import lims.api.pr.vo.RePrintLabelRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RePrintLabelRequestServiceImpl implements RePrintLabelRequestService {

    private final RePrintLabelRequestDao rePrintLabelRequestDao;
    private final ApproveService approveService;

    // 라벨재출력요청 조회
    @Override
    public List<RePrintLabelRequestVO> findAll(RePrintLabelRequestVO param) {
        param.setPitmCdForDecode(PItemType.RAW_MATERIAL.getCode());
//        param.setAddSmpProc(AddSampleProgress.APPROVE_ADD_SAMPLE.getCode());
        List<String> labelPrtProcList = Arrays.asList(LabelPrtProcess.LABEL_PRINT_PROCESSED.getCode(), LabelPrtProcess.RE_LABEL_PRINT_REJECT.getCode(), LabelPrtProcess.RE_LABEL_PRINT_APPROVED.getCode());
        param.setLabelPrtProcList(labelPrtProcList);
        return rePrintLabelRequestDao.findAll(param);
    }

    // 라벨재출력요청 모달 조회
    @Override
    public List<RePrintLabelRequestVO> findLabelList(RePrintLabelRequestVO param) {
//        param.setPitmCdForDecode(PItemType.RAW_MATERIAL.getCode());
//        param.setAddSmpProc(AddSampleProgress.APPROVE_ADD_SAMPLE.getCode());
        return rePrintLabelRequestDao.findLabelList(param);
    }

    // 라벨재출력요청
    @Override
    @Transactional
    public int rePrintLabelRequest(RePrintLabelRequestVO param) {
        int result = 0;

        ApproveVO approveVO = this.setApproveVO(param);
        approveService.requestApprove(approveVO);

        RePrintLabelRequestVO qsSmpLabelPrtDto = new RePrintLabelRequestVO();
        qsSmpLabelPrtDto.setLabelRePrtAprIdx(approveVO.getAprIdx());
        qsSmpLabelPrtDto.setLabelPrtIdx(param.getLabelPrtIdx());
        qsSmpLabelPrtDto.setPrtSeq(param.getPrtSeq());
        qsSmpLabelPrtDto.setLabelRptIdx(param.getLabelRptIdx());
        qsSmpLabelPrtDto.setLabelPrtProc(LabelPrtProcess.RE_LABEL_PRINT_REQUEST.getCode());
        result += rePrintLabelRequestDao.rePrintLabelRequest(qsSmpLabelPrtDto);

        RePrintLabelRequestVO syRptMstDto = new RePrintLabelRequestVO();
        syRptMstDto.setRptIdx(param.getLabelRptIdx());
        syRptMstDto.setRptDiv(param.getRptDiv());
        result += rePrintLabelRequestDao.updateSyRptMst(syRptMstDto);

        return result;
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

    public ApproveVO setApproveVO(RePrintLabelRequestVO param) {
        ApproveVO approveVO = new ApproveVO();
        // 내일 할 일 ☆, S05 코드 새로 발급 받아야한다.
//        approveVO.setAprReqDiv(ApproveRequestStDivType.STAB_PLAN_VERSION.getCode());
        if(param.getAprUid() != null) {
            approveVO.setAprUid(param.getAprUid());
        }

        return approveVO;
    }
}
