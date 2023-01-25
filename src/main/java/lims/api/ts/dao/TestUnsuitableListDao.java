package lims.api.ts.dao;

import lims.api.ts.vo.TestUnsuitableListVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TestUnsuitableListDao {

    List<TestUnsuitableListVO> findAll(TestUnsuitableListVO param);

    List<TestUnsuitableListVO> findResultItem(TestUnsuitableListVO param);

    //TODO AUDIT
    int save(TestUnsuitableListVO param);
}
