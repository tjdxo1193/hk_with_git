package lims.api.sc.dao;

import lims.api.ms.vo.SpecManageAitmVO;
import lims.api.ms.vo.SpecManageDptVO;
import lims.api.ms.vo.SpecManagePitmVO;
import lims.api.ms.vo.SpecManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface SearchForSpecHisDao {

    List<SpecManagePitmVO> getPItemList(SpecManageVO param);

    List<SpecManageVO> getVersionList(SpecManageVO param);

    List<SpecManageAitmVO> getAItemList(SpecManageVO param);

    List<SpecManageAitmVO> getSemiAItemList(SpecManageVO param);

    List<SpecManageDptVO> getDepartmentList(SpecManageVO param);
}
