package lims.api.pr.service;

import lims.api.pr.vo.RePrintLabelRequestVO;

import java.util.List;

public interface RePrintLabelRequestService {

    // 라벨재출력요청 조회
    List<RePrintLabelRequestVO> findAll(RePrintLabelRequestVO param);
    // 라벨재출력요청 모달 조회
    List<RePrintLabelRequestVO> findLabelList(RePrintLabelRequestVO param);
    // 라벨재출력요청
    int rePrintLabelRequest(RePrintLabelRequestVO param);
    // 라벨재출력취소
    int rePrintLabelCancel(RePrintLabelRequestVO param);
}
