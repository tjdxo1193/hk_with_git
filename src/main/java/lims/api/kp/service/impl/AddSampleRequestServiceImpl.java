package lims.api.kp.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.kp.dao.AddSampleRequestDao;
import lims.api.kp.enums.AddSampleProgress;
import lims.api.kp.enums.ApproveRequestDivision;
import lims.api.kp.service.AddSampleRequestService;
import lims.api.kp.vo.AddSampleRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddSampleRequestServiceImpl implements AddSampleRequestService {
    private final AddSampleRequestDao dao;
    private final ApproveService approveService;

    @Override
    public List<AddSampleRequestVO> find(AddSampleRequestVO param) {
        return dao.find(param);
    }

    @Override
    public void create(AddSampleRequestVO param) {
        int result = 0;
        param.setAddSmpProc(AddSampleProgress.TEMP_SAVE.getCode());
        param.setLabelCd(makeLabelCd(param));
        result += dao.create(param);
        if (result < 1) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void update(AddSampleRequestVO param) {
        int result = 0;
        result += dao.update(param);
        if (result < 1) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void delete(AddSampleRequestVO param) {
        int result = 0;
        result += dao.delete(param);
        if (result < 1) {
            throw new NoDeletedDataException();
        }
    }

    @Override
    public void requestApprove(AddSampleRequestVO param) {
        int result = 0;
        param.setAprReqDiv(ApproveRequestDivision.ADD_SAMPLE.getCode());
        ApproveVO approveInfo = setApproveInfo(param);
        approveService.create(approveInfo);
        param.setAddSmpProc(AddSampleProgress.REQUEST_ADD_SAMPLE.getCode());
        param.setAddSmpAprReqIdx(approveInfo.getAprIdx());
        result += dao.requestApprove(param);
        if (result < 1) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public String makeLabelCd(AddSampleRequestVO param) {
        String andIdx = param.getAnsIdx().toString();
        return andIdx.concat(dao.findLabelCd(param));
    }

    private ApproveVO setApproveInfo(AddSampleRequestVO param) {
        ApproveVO approveInfo = new ApproveVO();
        approveInfo.setPlntCd(param.getPlntCd());
        approveInfo.setAprReqDiv(param.getAprReqDiv());
        approveInfo.setAprUid(param.getAprUid());
        approveInfo.setAprReqUid(param.getAprReqUid());
        return approveInfo;
    }
}
