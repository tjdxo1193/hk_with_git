package lims.api.sm.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.sm.dao.PackagingSpecimenApproveDao;
import lims.api.sm.service.PackagingSpecimenApproveService;
import lims.api.sm.vo.PackagingSpecimenApproveVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PackagingSpecimenApproveServiceImpl implements PackagingSpecimenApproveService {

    private final PackagingSpecimenApproveDao dao;
    private final ApproveService approveService;

    @Override
    public List<PackagingSpecimenApproveVO> getPackagingSpecimenList(PackagingSpecimenApproveVO request) {
        return dao.getPackagingSpecimenList(request);
    }

    @Override
    public List<PackagingSpecimenApproveVO> getPackagingSpecimenAcsr(PackagingSpecimenApproveVO request) {
        return dao.getPackagingSpecimenAcsr(request);
    }

    @Override
    public void approve(PackagingSpecimenApproveVO request) {
        approveService.approve(request.getPmSpcmDelAprIdx());
        int result = dao.approve(request);
        if(result != 0){
            dao.updatePackagingSpecimenProcess(request);
            List<PackagingSpecimenApproveVO> list = dao.getPackagingSpecimenAcsr(request);
            for (PackagingSpecimenApproveVO item : list) {
                dao.deletePackagingSpecimenAcsr(item);
            }
        }
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void reject(PackagingSpecimenApproveVO request) {
        int result = dao.reject(request);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}
