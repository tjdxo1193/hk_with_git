package lims.api.an.service.Impl;

import lims.api.an.dao.AnalColOpenDisDao;
import lims.api.an.enums.AnalColMaterialProcess;
import lims.api.an.service.AnalColOpenDisService;
import lims.api.an.vo.AnalColMaterialVO;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalColOpenDisServiceImpl implements AnalColOpenDisService {

    private final AnalColOpenDisDao dao;
    private final ApproveService approveService;

    @Override
    public List<AnalColMaterialVO> findAll(AnalColMaterialVO param) {
        List processCodeList = new ArrayList();
        param.setEtrProcCd(AnalColMaterialProcess.APPROVE_COMPLETE.getProcessCode());

        processCodeList.add(AnalColMaterialProcess.STOCK.getProcessCode());
        processCodeList.add(AnalColMaterialProcess.OPEN.getProcessCode());
        param.setProcessCodeList(processCodeList);

        return dao.findAll(param);
    }

    @Override
    public void open(AnalColMaterialVO param) {
        param.setMngProcCd(AnalColMaterialProcess.OPEN.getProcessCode());
        int result = dao.update(param);
        result += dao.open(param);

        if(result != 2) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void update(AnalColMaterialVO param) {
        int result = dao.update(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void disposal(AnalColMaterialVO param) {
        ApproveVO approveInfo = setApproveVO(param);
        approveService.create(approveInfo);

        param.setDpsReqAprIdx(approveInfo.getAprIdx());
        if(AnalColMaterialProcess.OPEN.getProcessCode().equals(param.getMngProcCd())) {
            param.setMngProcCd(AnalColMaterialProcess.DISPOSAL_REQUEST_IN_OPEN.getProcessCode());
        }else {
            param.setMngProcCd(AnalColMaterialProcess.DISPOSAL_REQUEST_IN_STOCK.getProcessCode());
        }

        int result = dao.disposal(param);

        if(result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    private ApproveVO setApproveVO(AnalColMaterialVO param) {
        ApproveVO approveInfo = new ApproveVO();
        approveInfo.setPlntCd(param.getPlntCd());
        approveInfo.setAprReqDiv(param.getAprReqDiv());
        approveInfo.setAprUid(param.getAprUid());
        approveInfo.setAprReqUid(param.getAprReqUid());

        return approveInfo;
    }
}