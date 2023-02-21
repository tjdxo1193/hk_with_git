package lims.api.integration.dao;

import lims.api.integration.vo.ELNCtReportVO;
import lims.api.integration.vo.ELNSendVO;
import lims.api.integration.vo.ELNStandardSpecVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ELNDao {

    List<ELNCtReportVO.Matnr> findReportCtByDegree(Integer degree);

    ELNCtReportVO.File findReportCtFileByDegree(Integer degree);

    int nextDegreeInReportCt();

    int nextIdxInReportCtFile();

    int createReportCtFile(ELNCtReportVO.File vo);


    int nextIdxInReportCtMatnr();

    int createReportCtMatnr(ELNCtReportVO.Matnr vo);


    int nextDegreeInTestMethodByItem();

    int nextIdxInTestMethodByItem();

    int createTestMethodByItem(ELNSendVO.TestMethodByItem vo);

    int updateTrsStatusOfTestMethodByItem(ELNSendVO.TestMethodByItem vo);


    List<ELNSendVO.TestMethodByItem> findTestMethodByItemIds(String idsForInClause);


    List<ELNStandardSpecVO> findLastVersionStandardSpecAll(Integer degree);

    int nextIdxInStandardSpec();

    int nextDegreeInStandardSpec();

    int createStandardSpec(ELNStandardSpecVO vo);

}