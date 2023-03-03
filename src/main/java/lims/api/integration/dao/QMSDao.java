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


    Integer nextDegreeInMaterial();

    Integer nextIdxInMaterial();

    int createMaterial(QMSSendVO.MaterialAll param);

    int updateMaterial(QMSSendVO.MaterialAll param);


    Integer nextIdxInCosmetic(Integer materialIdx);

    int createCosmetic(QMSSendVO.CosmeticMaterial param);

    int updateCosmetic(QMSSendVO.CosmeticMaterial param);


    Integer nextIdxInCosmeticBulk(Integer materialIdx);

    int createCosmeticBulk(QMSSendVO.CosmeticBulkMaterial param);

    int updateCosmeticBulk(QMSSendVO.CosmeticBulkMaterial param);


    Integer nextIdxInDrug(Integer materialIdx);

    int createDrug(QMSSendVO.DrugMaterial param);

    int updateDrug(QMSSendVO.DrugMaterial param);


    Integer nextIdxInDrugBulk(Integer materialIdx);

    int createDrugBulk(QMSSendVO.DrugBulkMaterial param);

    int updateDrugBulk(QMSSendVO.DrugBulkMaterial param);

}