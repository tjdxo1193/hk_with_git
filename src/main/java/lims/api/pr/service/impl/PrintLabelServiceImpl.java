package lims.api.pr.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.vo.ApproveVO;
import lims.api.ms.enums.PItemType;
import lims.api.pr.dao.PrintLabelDao;
import lims.api.pr.enums.LabelPrtProcess;
import lims.api.pr.service.PrintLabelService;
import lims.api.pr.vo.PrintLabelVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrintLabelServiceImpl implements PrintLabelService {

    private final PrintLabelDao printLabelDao;

    // 라벨출력 조회
    @Override
    public List<PrintLabelVO> findAll(PrintLabelVO param) {
        param.setPitmCdForDecode(PItemType.RAW_MATERIAL.getCode());
//        param.setAddSmpProc(AddSampleProgress.APPROVE_ADD_SAMPLE.getCode());
        param.setLabelPrtProc(LabelPrtProcess.RE_LABEL_PRINT_APPROVED.getCode());
        return printLabelDao.findAll(param);
    }

    @Override
    @Transactional
    public int printLabel(List<PrintLabelVO> param) {
        int result = 0;

        for(PrintLabelVO item : param) {
            // 최초 출력(QS 검체 라벨 출력 테이블은 INSERT, QT 품목 시험 정보 테이블은 UPDATE 되어야 한다.)
            PrintLabelVO qsSmpLabelPrtDto = new PrintLabelVO();
            if (item.getLabelPrtIdx() != null) {
                qsSmpLabelPrtDto.setLabelPrtIdx(item.getLabelPrtIdx());
            }
            qsSmpLabelPrtDto.setLabelPrtProc(LabelPrtProcess.LABEL_PRINT_PROCESSED.getCode());
            qsSmpLabelPrtDto.setPrtUid(item.getLoginUserUid());
            result += printLabelDao.createQsSmpLabelPrt(qsSmpLabelPrtDto);

            // SY 리포트 마스터(SY_RPT_MST) 테이블에 새로 발급받은 RPT_IDX를 이용해서 INSERT
            // 실제 들어가는 키: RPT_IDX, 이용하는 키: PRT_IDX
            PrintLabelVO syRptMstDto = new PrintLabelVO();
            Integer labelPrtIdx = qsSmpLabelPrtDto.getLabelPrtIdx();
            Integer prtSeq = item.getPrtSeq();

            syRptMstDto.setLabelPrtIdx(labelPrtIdx);
            syRptMstDto.setPrtSeq(prtSeq);
            syRptMstDto.setRptDiv(item.getRptDiv());
            syRptMstDto.setRptNm(null);
            syRptMstDto.setRptRdPath(null);
            syRptMstDto.setRptRmk(null);
            syRptMstDto.setEtcRmk(null);
            result += printLabelDao.createSyRptMst(syRptMstDto);

            // QT 품목 시험 정보(QT_PITM_ANS_INFO) 테이블의 검체 라벨 출력 IDX(SMP_LABEL_PRT_IDX) UPDATE
            if (Optional.ofNullable(item.getLabelPrtIdx()).orElse(0) == 0) {
                PrintLabelVO qtPitmAnsInfoDto = new PrintLabelVO();
                Integer smpLabelPrtIdx = qsSmpLabelPrtDto.getLabelPrtIdx();
                qtPitmAnsInfoDto.setPlntCd(item.getPlntCd());
                qtPitmAnsInfoDto.setAnsIdx(item.getAnsIdx());
                qtPitmAnsInfoDto.setSmpLabelPrtIdx(smpLabelPrtIdx);
                result += printLabelDao.updateQtPitmAnsInfoSmpLabelPrtIdx(qtPitmAnsInfoDto);
            }
        }

        if(result == 0) {
            throw new NoUpdatedDataException();
        }

        return result;
    }

    public ApproveVO setApproveVO(PrintLabelVO param) {
        ApproveVO approveVO = new ApproveVO();
        approveVO.setPlntCd(param.getPlntCd());
        approveVO.setAprReqUid(param.getLoginUserUid());
        // 내일 할 일 ☆, S05 코드 새로 발급 받아야한다.
//        approveVO.setAprReqDiv(ApproveRequestStDivType.STAB_PLAN_VERSION.getCode());
//        if(param.getAprUid() != null) {
//            approveVO.setAprUid(param.getAprUid());
//        }

        return approveVO;
    }
}
