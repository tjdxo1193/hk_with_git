package lims.api.sm.service;

import lims.api.sm.vo.PackagingSpecimenManageVO;

import java.util.List;

public interface PackagingSpecimenManageService {
    List<PackagingSpecimenManageVO> getPackagingSpecimenList(PackagingSpecimenManageVO request);
    List<PackagingSpecimenManageVO> getPackagingSpecimenAcsr(PackagingSpecimenManageVO request);

    void save(PackagingSpecimenManageVO request);

    void deleteRequest(PackagingSpecimenManageVO request);
}
