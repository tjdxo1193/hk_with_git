package lims.api.ms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import lims.api.ms.vo.TestItemPerManageVO;

@Mapper
@Repository
public interface TestItemPerManageDao {

	List<TestItemPerManageVO> getList(TestItemPerManageVO param);	
	List<TestItemPerManageVO> getPerList(TestItemPerManageVO param);
	int putTestItemPer(TestItemPerManageVO param);
}
