package lims.api.integration.dao;

import lims.api.integration.vo.ELNCtReportVO;
import lims.api.integration.vo.ELNStandardSpecVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ELNMasterDao {

    List<ELNStandardSpecVO> findStandardSpecAll();

    int deleteStandardSpec();

    int createStandardSpec(ELNStandardSpecVO vo);

//
    List<ELNStandardSpecVO> findStandardSpecDtlAll();
//
//    ELNStandardSpecVO findStandardSpecDtlById(KeyObject keyObject);

    int deleteStandardSpecDtl();

    int createStandardSpecDtl(ELNStandardSpecVO vo);


    List<ELNCtReportVO.Matnr> findCtReport();

    int createCtReport(ELNCtReportVO.Matnr vo);


    List<ELNCtReportVO.File> findCtReportFile();

    int createCtReportFile(ELNCtReportVO.File vo);

    int updateCtReportFile(ELNCtReportVO.File vo);


}