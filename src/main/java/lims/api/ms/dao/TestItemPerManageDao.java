package lims.api.ms.dao;

import lims.api.ms.vo.TestItemPerManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TestItemPerManageDao {

	List<TestItemPerManageVO> getList(TestItemPerManageVO param);	
	List<TestItemPerManageVO> getPerList(TestItemPerManageVO param);
	int putTestItemPer(TestItemPerManageVO param);
    String checkDuplicateAmitemCd(TestItemPerManageVO param);
}
