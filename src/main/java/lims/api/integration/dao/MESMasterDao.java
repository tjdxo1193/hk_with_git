package lims.api.integration.dao;

import lims.api.integration.vo.MESFinalOrderVO;
import lims.api.integration.vo.MESPackageSpecReportVO;
import lims.api.integration.vo.SRMFinalOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MESMasterDao {

    List<MESFinalOrderVO> findFinalOrder();

    int createFinalOrder(MESFinalOrderVO param);


    List<MESPackageSpecReportVO> findPackageSpec();

    int createPackageSpec(MESPackageSpecReportVO param);

    int updatePackageSpec(MESPackageSpecReportVO param);

}