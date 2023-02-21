package lims.api.ms.dao;

import lims.api.ms.vo.SpecApprVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpecApprDao {

    List<SpecApprVO> getVersionList(SpecApprVO param);
    List<SpecApprVO> getAItmList(SpecApprVO param);


    int updateApproval(SpecApprVO mvo);

    int updateReject(SpecApprVO mvo);
}
