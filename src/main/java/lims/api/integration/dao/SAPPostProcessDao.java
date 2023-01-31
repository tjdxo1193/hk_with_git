package lims.api.integration.dao;

import lims.api.integration.vo.SAPPostProcessVO;
import lims.api.integration.vo.SAPTestRequestVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SAPPostProcessDao {
    
    boolean existsPItem(SAPPostProcessVO.Material.PItemKey param);

    Integer currentVersionOfPItem(SAPPostProcessVO.Material.PItemKey param);

    Integer nextVersionOfPItem(SAPPostProcessVO.Material.PItemKey param);

    SAPPostProcessVO.Material.PItemSpec currentVersionOfSpec(SAPPostProcessVO.Material.PItemKey param);

    Integer nextPItemSpecIdxOfSpec(SAPPostProcessVO.Material.PItemKey param);

    String currentFinalVersionStatusCodeOfPItem(SAPPostProcessVO.Material.PItemKey param);

    String currentFinalVersionStatusCodeOfSpec(SAPPostProcessVO.Material.PItemKey param);

    Integer findLatestApprovedPackageTest(SAPPostProcessVO.Material.PItemSpec param);

    int createPItem(SAPPostProcessVO.Material.PItem param);

    int createPItemInfo(SAPPostProcessVO.Material.PItemInfo param);

    int createPItemInfoSap(SAPPostProcessVO.Material.PItemInfoSap param);

    int createNewPItemSpec(SAPPostProcessVO.Material.PItemSpec param);

    int createNextPItemSpec(SAPPostProcessVO.Material.PItemSpec param);

    int updateToNullPItemSpecVersion(SAPPostProcessVO.Material.PItemKey param);

    SAPPostProcessVO.Material.PItemSpecVersion findPItemSpecVersionNextKey(SAPPostProcessVO.Material.PItemSpecVersion param);

    int updatePItem(SAPPostProcessVO.Material.PItem param);

    int updatePItemInfo(SAPPostProcessVO.Material.PItemInfo param);

    int updatePItemInfoSap(SAPPostProcessVO.Material.PItemInfoSap param);

    int updateStatusOfPItemSpec(SAPPostProcessVO.Material.PItemSpec param);


    List<Integer> findReqIdxByIspReqNo(String ispReqNo);

    List<SAPPostProcessVO.TestRequest.PItemReqProcess> findNotCanceledTestReqProcessAllByReqIdx(String idxesStr);

    int nextIdxInPItemReq();

    int createPItemRequest(SAPPostProcessVO.TestRequest.PItemReq param);

    int nextSeqInPItemRequestNonCfm(SAPPostProcessVO.TestRequest.PItemReqNonCfm param);

    int createPItemRequestNonCfm(SAPPostProcessVO.TestRequest.PItemReqNonCfm param);

    int cancelTestRequestProcessToCancel(SAPPostProcessVO.TestRequest.PItemReqProcess param);

    List<SAPTestRequestVO.RequestHeader> findAllForReqTest();

    SAPPostProcessVO.Material.PItemInfo findPItmInfoAllByKey(SAPPostProcessVO.Material.PItemKey param);

    List<SAPPostProcessVO.Material.PItemInfoSap> findAllByLabNo(SAPPostProcessVO.Material.PItemKey param);

}