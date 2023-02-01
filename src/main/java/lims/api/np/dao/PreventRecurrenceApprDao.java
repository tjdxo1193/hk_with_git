package lims.api.np.dao;

import lims.api.np.vo.PreventRecurrenceApprVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PreventRecurrenceApprDao {
    List<PreventRecurrenceApprVO> findAll(PreventRecurrenceApprVO param);

    List<PreventRecurrenceApprVO> findResultItem(PreventRecurrenceApprVO param);
}
