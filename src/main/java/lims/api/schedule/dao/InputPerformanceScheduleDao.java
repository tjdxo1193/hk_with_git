package lims.api.schedule.dao;

import lims.api.schedule.vo.InputPerformVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InputPerformanceScheduleDao {

    List<InputPerformVO.Manufacture> findManufactureFailedRecord(String failStatusCode);

    List<InputPerformVO.Purchase> findPurchaseFailedRecord(String failStatusCode);

}