package lims.api.integration.dao;

import lims.api.integration.vo.ELNPostProcessVO;
import lims.api.integration.vo.ELNStandardSpecVO;
import lims.api.integration.vo.SAPPostProcessVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ELNPostProcessDao {
    
    List<ELNPostProcessVO.PItemSpec> findFinalSpecByLabNoAndPrdDiv(ELNStandardSpecVO.DifferentKey param);

    int nextIdxOfSpec(ELNPostProcessVO.PItemSpec vo);

    int createNewVersionOfSpec(ELNPostProcessVO.PItemSpec vo);

    int updateStatusOfSpec(ELNPostProcessVO.PItemSpec vo);

    int updateAitmSpecIdxToNullOfSpec(ELNPostProcessVO.PItemSpec vo);


//
//    Integer nextVersionOfSpec(SAPPostProcessVO.Material.PItemKey param);
//
//    Integer nextPItemSpecIdxOfSpec(SAPPostProcessVO.Material.PItemKey param);
//
//    String currentFinalVersionStatusCodeOfSpec(SAPPostProcessVO.Material.PItemKey param);

}