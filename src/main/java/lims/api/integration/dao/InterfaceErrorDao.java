package lims.api.integration.dao;

import lims.api.integration.vo.InterfaceErrorVO;
import lims.api.integration.vo.InterfacePostProcessErrorVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InterfaceErrorDao {

    int nextId();

    void createLog(InterfaceErrorVO vo);


    int nextIdOfPostProcess();

    int createLogOfPostProcess(InterfacePostProcessErrorVO vo);

    InterfacePostProcessErrorVO findPostProcessById(Integer errorLogId);

}