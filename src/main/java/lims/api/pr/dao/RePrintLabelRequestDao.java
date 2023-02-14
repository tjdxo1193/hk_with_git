package lims.api.pr.dao;

import lims.api.pr.vo.RePrintLabelRequestVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RePrintLabelRequestDao {
    // 라벨재출력요청 조회
    List<RePrintLabelRequestVO> findAll(RePrintLabelRequestVO param);
    // 라벨재출력요청 모달 조회
    List<RePrintLabelRequestVO> findLabelList(RePrintLabelRequestVO param);
    // 라벨재출력요청
    int rePrintLabelRequest(RePrintLabelRequestVO param);
    // 라벨재출력취소
    int rePrintLabelCancel(RePrintLabelRequestVO param);

}
