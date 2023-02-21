package lims.api.integration.dao;

import lims.api.integration.vo.QMSSendVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QMSDao {

    List<QMSSendVO.ShiptReq> findShiptReqByBatchNo(String batchNo);

    List<QMSSendVO.ShiptPerform> findMESShiptPerformByKey(QMSSendVO.ShiptTest param);

    List<QMSSendVO.ShiptPerform> findSRMShiptPerformByKey(QMSSendVO.ShiptTest param);

    Integer nextDegreeInShipt();

    Integer nextIdxInShipt();

    int createShipt(QMSSendVO.ShiptReq param);

    int updateShipt(QMSSendVO.ShiptReq param);

    int createShiptTest(QMSSendVO.ShiptTest param);

    int createShiptPerform(QMSSendVO.ShiptPerform param);


    List<QMSSendVO.ShiptTest> findMESTestAllByOrderNoAndLotNo(QMSSendVO.ShiptReq param);

    List<QMSSendVO.ShiptTest> findSRMTestAllByOrderNoAndLotNo(QMSSendVO.ShiptReq param);

    List<QMSSendVO.ShiptValidate> findMESFinalOrdersByOrderNoAndLotNo(QMSSendVO.ShiptReq param);

    List<QMSSendVO.ShiptValidate> findSRMFinalOrdersByOrderNoAndLotNo(QMSSendVO.ShiptReq param);

}