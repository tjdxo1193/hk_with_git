package lims.api.ms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import lims.api.ms.vo.TestItemManageVO;

@Mapper
@Repository
public interface TestItemManageDao {

	List<TestItemManageVO> getList(TestItemManageVO param);
	int putTestItem(TestItemManageVO param);
}
