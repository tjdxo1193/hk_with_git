package lims.api.sm.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.sm.dao.PackagingSpecimenManageDao;
import lims.api.sm.service.PackagingSpecimenManageService;
import lims.api.sm.vo.PackagingSpecimenManageVO;
import lims.api.ts.vo.TestResultCancelVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PackagingSpecimenManageServiceImpl implements PackagingSpecimenManageService {

    private final PackagingSpecimenManageDao dao;
    private final ApproveService approveService;

    @Override
    public List<PackagingSpecimenManageVO> getPackagingSpecimenList(PackagingSpecimenManageVO request) {
        return dao.getPackagingSpecimenList(request);
    }

    @Override
    public List<PackagingSpecimenManageVO> getPackagingSpecimenAcsr(PackagingSpecimenManageVO request) {
        return dao.getPackagingSpecimenAcsr(request);
    }

    @Override
    public void save(PackagingSpecimenManageVO request) {
        int result;

        if (request.getPmSpcmIdx() == 0) {
            int count = dao.getPackagingSpecimenNoCount(request);
            if(count == 0){
                dao.insertPackagingSpecimenNo(request);
            }else{
                dao.updatePackagingSpecimenNo(request);
            }
            result = dao.insertPackagingSpecimen(request);
        } else {
            result = dao.updatePackagingSpecimen(request);
        }

        //구성품 추가
        if (request.getAddedRowItems().size() > 0) {
            List<PackagingSpecimenManageVO> list = request.getAddedRowItems();
            for(PackagingSpecimenManageVO row : list) {
                row.setPmSpcmIdx(request.getPmSpcmIdx());
                dao.insertPackagingSpecimenAcsr(row);
            }
        }
        //구성품 수정
        if (request.getEditedRowItems().size() > 0) {
            List<PackagingSpecimenManageVO> list = request.getEditedRowItems();
            for(PackagingSpecimenManageVO row : list) {
                dao.updatePackagingSpecimenAcsr(row);
            }
        }
        //구성품 삭제
        if (request.getRemovedRowItems().size() > 0) {
            List<PackagingSpecimenManageVO> list = request.getRemovedRowItems();
            for(PackagingSpecimenManageVO row : list) {
                dao.deletePackagingSpecimenAcsr(row);
            }
        }

        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void deleteRequest(PackagingSpecimenManageVO request) {
        int pmSpcmDelAprIdx = approveService.requestApprove(request.getApproveInfo());
        request.setPmSpcmDelAprIdx(pmSpcmDelAprIdx);
        int result = dao.deleteRequest(request);
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }
}
