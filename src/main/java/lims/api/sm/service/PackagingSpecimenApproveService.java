package lims.api.sm.service;

import lims.api.sm.vo.PackagingSpecimenApproveVO;

import java.util.List;

public interface PackagingSpecimenApproveService {
    List<PackagingSpecimenApproveVO> getPackagingSpecimenList(PackagingSpecimenApproveVO request);
    List<PackagingSpecimenApproveVO> getPackagingSpecimenAcsr(PackagingSpecimenApproveVO request);

    void approve(PackagingSpecimenApproveVO request);
    void reject(PackagingSpecimenApproveVO request);
}
