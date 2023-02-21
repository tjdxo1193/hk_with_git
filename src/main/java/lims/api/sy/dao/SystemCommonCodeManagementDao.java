package lims.api.sy.dao;

import lims.api.sy.vo.SystemCommonCodeManagementVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SystemCommonCodeManagementDao {
    List<SystemCommonCodeManagementVO> getSyCmCodeHir(SystemCommonCodeManagementVO request);

    List<SystemCommonCodeManagementVO> getSyCmCodeDtl(SystemCommonCodeManagementVO request);
}
