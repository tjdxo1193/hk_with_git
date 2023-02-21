package lims.api.integration.dao;

import lims.api.integration.domain.eai.TrsStateful;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InterfaceCommonDao {

    int nextIdxInTestStatus();

    int nextDegreeInTestStatus();

    int updateTrsStatusOfTestStatus(TrsStateful vo);


    int nextIdxInTestResult();

    int nextDegreeInTestResult();

    int updateTrsStatusOfTestResult(TrsStateful vo);

}