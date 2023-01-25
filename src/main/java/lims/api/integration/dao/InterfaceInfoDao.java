package lims.api.integration.dao;

import lims.api.integration.vo.IfInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InterfaceInfoDao {

    int nextId();

    int createInfo(IfInfoVO data);

    int updateStatusInfo(IfInfoVO data);

    IfInfoVO findById(Integer idx);

}