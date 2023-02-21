package lims.api.ms.dao;

import lims.api.ms.vo.SpecApprRequestVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpecApprRequestDao {

    List<SpecApprRequestVO> getVersionList(SpecApprRequestVO param);

    List<SpecApprRequestVO> getAItemList(SpecApprRequestVO param);

    int updateReviewReturn(SpecApprRequestVO param);

    int updateApprovalRequest(SpecApprRequestVO param);
}
