package lims.api.integration.dao;

import lims.api.integration.vo.SAPBomVO;
import lims.api.integration.vo.SAPCharacteristicVO;
import lims.api.integration.vo.SAPInputPerformanceByBatchVO;
import lims.api.integration.vo.SAPMaterialVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SAPMasterDao {

    List<SAPMaterialVO.Mara> findMaterialMaraAll();

    List<SAPMaterialVO.Mara> findMaterialMara(List<SAPMaterialVO.Mara> mara);

    int createMaterialMara(SAPMaterialVO.Mara param);

    int updateMaterialMara(SAPMaterialVO.Mara param);


    List<SAPMaterialVO.Marc> findMaterialMarcAll();

    List<SAPMaterialVO.Marc> findMaterialMarc(List<SAPMaterialVO.Marc> param);

    List<SAPMaterialVO.Marc> findMaterialMarcByMatnr(String matnr);

    int createMaterialMarc(SAPMaterialVO.Marc param);

    int updateMaterialMarc(SAPMaterialVO.Marc param);


    List<SAPMaterialVO.Mvke> findMaterialMvke(List<SAPMaterialVO.Mvke> param);

    int createMaterialMvke(SAPMaterialVO.Mvke param);

    int updateMaterialMvke(SAPMaterialVO.Mvke param);

    List<SAPMaterialVO.Zmdv> findMaterialZmdvByMaterialCode(String meterialCode);

    List<SAPMaterialVO.Zmdv> findMaterialZmdv(List<SAPMaterialVO.Zmdv> param);

    int createMaterialZmdv(SAPMaterialVO.Zmdv param);

    int updateMaterialZmdv(SAPMaterialVO.Zmdv param);


    List<SAPMaterialVO.Makt> findMaterialMakt(List<SAPMaterialVO.Makt> param);

    int createMaterialMakt(SAPMaterialVO.Makt param);

    int updateMaterialMakt(SAPMaterialVO.Makt param);


    int createBOM(SAPBomVO param);

    int deleteBOM();


    List<SAPCharacteristicVO.Cabn> findCharacteristicCabn();

    int createCharacteristicCabn(SAPCharacteristicVO.Cabn param);

    int updateCharacteristicCabn(SAPCharacteristicVO.Cabn param);


    List<SAPCharacteristicVO.Ksml> findCharacteristicKsml();

    int createCharacteristicKsml(SAPCharacteristicVO.Ksml param);

    int updateCharacteristicKsml(SAPCharacteristicVO.Ksml param);


    List<SAPInputPerformanceByBatchVO.InputPerformanceHeader> findInputPerformHeader();

    int createInputPerformHeader(SAPInputPerformanceByBatchVO.InputPerformanceHeader param);

    int updateInputPerformHeader(SAPInputPerformanceByBatchVO.InputPerformanceHeader param);


    List<SAPInputPerformanceByBatchVO.InputPerformanceDetail> findInputPerformDetail();

    int createInputPerformDetail(SAPInputPerformanceByBatchVO.InputPerformanceDetail param);

    int updateInputPerformDetail(SAPInputPerformanceByBatchVO.InputPerformanceDetail param);

}