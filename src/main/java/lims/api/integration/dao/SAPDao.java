package lims.api.integration.dao;

import lims.api.integration.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SAPDao {

    List<SAPBomVO> findBOMByDegree(int degree);

    int nextDegreeInBOM();

    int nextIdxInBOM();

    int createBOM(SAPBomVO vo);


    int nextDegreeInMaterial();

    List<SAPMaterialVO.Mara> findMaraAllByDegree(int degree);

    int nextIdxInMaterialMara();

    int createMaterialMara(SAPMaterialVO.Mara vo);


    List<SAPMaterialVO.Marc> findMarcAllByDegree(int degree);

    int nextIdxInMaterialMarc();

    int createMaterialMarc(SAPMaterialVO.Marc vo);


    List<SAPMaterialVO.Mvke> findMvkeAllByDegree(int degree);

    int nextIdxInMaterialMvke();

    int createMaterialMvke(SAPMaterialVO.Mvke vo);


    List<SAPMaterialVO.Zmdv> findZmdvAllByDegree(int degree);

    int nextIdxInMaterialZmdv();

    int createMaterialZmdv(SAPMaterialVO.Zmdv vo);


    List<SAPMaterialVO.Makt> findMaktAllByDegree(int degree);

    int nextIdxInMaterialMakt();

    int createMaterialMakt(SAPMaterialVO.Makt vo);


    List<SAPCharacteristicVO.Cabn> findCabnAllByDegree(int degree);

    int nextDegreeInCharacteristic();

    int nextIdxInCharacteristicCabn();

    int createCharacteristicCabn(SAPCharacteristicVO.Cabn vo);


    List<SAPCharacteristicVO.Ksml> findKsmlAllbyDegree(int degree);

    int nextIdxInCharacteristicKsml();

    int createCharacteristicKsml(SAPCharacteristicVO.Ksml vo);


    SAPTestRequestVO.RequestHeader findOneTestReqByDegree(int degree);

    List<SAPTestRequestVO.RequestDetails> findAllTestReqDetailsByDegree(int degree);

    int nextDegreeInTestRequest();

    int nextIdxInTestRequestHeaders();

    int createTestRequestHeader(SAPTestRequestVO.RequestHeader vo);


    int nextIdxInTestRequestDetails();

    int createTestRequestDetails(SAPTestRequestVO.RequestDetails vo);


    int nextIdxInCalendar();

    int nextDegreeInCalendar();

    int createCalendar(SAPCalendarVO vo);


    int createTestResult(SAPSendVO.TestResult vo);


    int createTestStatus(SAPSendVO.TestStatus vo);


    int nextIdxInTestPerformanceOfPurchaseInbound();

    int nextDegreeInTestPerformanceOfPurchaseInbound();

    int createTestPerformanceOfPurchaseInbound(SAPSendVO.TestPerformanceOfPurchaseInbound vo);

    int updateTrsStatusOfTestPerformanceOfPurchaseInbound(SAPSendVO.TestPerformanceOfPurchaseInbound vo);


    int nextIdxInTestPerformanceOfManufactureInbound();

    int nextDegreeInTestPerformanceOfManufactureInbound();

    int createTestPerformanceOfManufactureInbound(SAPSendVO.TestPerformanceOfManufactureInbound vo);

    int updateTrsStatusOfTestPerformanceOfManufactureInbound(SAPSendVO.TestPerformanceOfManufactureInbound vo);


    int nextIdxInAssetsMovementHistory();

    int nextDegreeInAssetsMovementHistory();

    int createAssetsMovementHistory(SAPSendVO.AssetsMovementHistory vo);

    int updateTrsStatusOfAssetsMovementHistory(SAPSendVO.AssetsMovementHistory vo);


    List<SAPInputPerformanceByBatchVO.InputPerformanceHeader> findInputPerformHeaderAllByDegree(int degree);

    int nextDegreeInInputPerformance();

    int nextIdxInInputPerformanceHeader();

    int createInputPerformanceHeader(SAPInputPerformanceByBatchVO.InputPerformanceHeader vo);


    List<SAPInputPerformanceByBatchVO.InputPerformanceDetail> findInputPerformDetailAllByDegree(int degree);

    int nextIdxInInputPerformanceDetail();

    int createInputPerformanceDetail(SAPInputPerformanceByBatchVO.InputPerformanceDetail vo);

}