package lims.api.integration.dao;

import lims.api.integration.vo.QMSSendVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QMSDao {

    List<QMSSendVO.ShiptBase> findShiptBaseByBatchNo(String batchNo);

    List<QMSSendVO.ShiptBase> findMESTestAllByOrderNoAndLotNo(QMSSendVO.ShiptBase param);

    List<QMSSendVO.ShiptBase> findSRMTestAllByOrderNoAndLotNo(QMSSendVO.ShiptBase param);

}