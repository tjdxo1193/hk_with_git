package lims.api.pr.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.vo.ApproveVO;
import lims.api.kp.enums.AddSampleProgress;
import lims.api.ms.enums.PItemType;
import lims.api.pr.dao.PrintLabelDao;
import lims.api.pr.enums.LabelPrtProcess;
import lims.api.pr.service.PrintLabelService;
import lims.api.pr.vo.PrintLabelVO;
import lims.api.st.enums.ApproveRequestStDivType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrintLabelServiceImpl implements PrintLabelService {

    private final PrintLabelDao printLabelDao;

    // 라벨출력 조회
    @Override
    public List<PrintLabelVO> findAll(PrintLabelVO param) {
        param.setPitmCdForDecode(PItemType.RAW_MATERIAL.getCode());
        param.setAddSmpProc(AddSampleProgress.APPROVE_ADD_SAMPLE.getCode());
        param.setLabelPrtProc(LabelPrtProcess.RE_LABEL_PRINT_APPROVED.getCode());
        return printLabelDao.findAll(param);
    }

    @Override
    @Transactional
    public int printLabel(PrintLabelVO param) {
        int result = 0;
        if(param.getLabelPrtIdx() == null) {
            try {
                // 최초 출력(QS 검체 라벨 출력 테이블은 INSERT, QT 품목 시험 정보 테이블은 UPDATE 되어야 한다.)
                PrintLabelVO qsSmpLabelPrt = new PrintLabelVO();
                qsSmpLabelPrt.setLabelPrtProc(LabelPrtProcess.LABEL_PRINT_PROCESSED.getCode());
                qsSmpLabelPrt.setLoginUserUid(param.getLoginUserUid());
                result += printLabelDao.createQsSmpLabelPrt(qsSmpLabelPrt);

                // SY 리포트 마스터(SY_RPT_MST) 테이블에 새로 발급받은 RPT_IDX를 이용해서 INSERT
                PrintLabelVO syRptMst = new PrintLabelVO();
                Integer labelPrttIdx = qsSmpLabelPrt.getLabelPrtIdx();
                syRptMst.setLabelPrtIdx(labelPrttIdx);
                syRptMst.setRptDiv(param.getRptDiv());
                syRptMst.setRptNm(null);
                syRptMst.setRptRdPath(null);
                syRptMst.setRptRmk(null);
                syRptMst.setEtcRmk(null);
                result += printLabelDao.createSyRptMst(syRptMst);

                // QT 품목 시험 정보(QT_PITM_ANS_INFO) 테이블의 검체 라벨 출력 IDX(SMP_LABEL_PRT_IDX) UPDATE
                PrintLabelVO qtPitmAnsInfo = new PrintLabelVO();
                Integer smpLabelPrtIdx = qsSmpLabelPrt.getLabelPrtIdx();
                qtPitmAnsInfo.setPlntCd(param.getPlntCd());
                qtPitmAnsInfo.setAnsIdx(param.getAnsIdx());
                qtPitmAnsInfo.setSmpLabelPrtIdx(smpLabelPrtIdx);
                result += printLabelDao.updateQtPitmAnsInfoSmpLabelPrtIdx(qtPitmAnsInfo);
            } catch(Exception e) {
                throw new NoUpdatedDataException();
            }
        } else {
            // 재출력
            ApproveVO approveVO = this.setApproveVO(param);

        }

        return 0;
    }

    public ApproveVO setApproveVO(PrintLabelVO param) {
        ApproveVO approveVO = new ApproveVO();
        approveVO.setPlntCd(param.getPlntCd());
        approveVO.setAprReqUid(param.getLoginUserUid());
        approveVO.setAprReqDiv(ApproveRequestStDivType.STAB_PLAN_VERSION.getCode());
//        if(param.getAprUid() != null) {
//            approveVO.setAprUid(param.getAprUid());
//        }

        return approveVO;
    }
}
