package lims.api.common.dao;

import lims.api.common.vo.UpdateDetectVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UpdateDetectDao {

    List<String> findPrimaryKeyColumnNames(UpdateDetectVO param);

    int updateDetectedAllColumns(UpdateDetectVO param);

    int updateDetectedCreatedColumns(UpdateDetectVO param);

    int updateDetectedUpdatedColumns(UpdateDetectVO param);

    boolean isExistsCreateColumns(UpdateDetectVO param);

    boolean isExistsUpdateColumns(UpdateDetectVO param);

}