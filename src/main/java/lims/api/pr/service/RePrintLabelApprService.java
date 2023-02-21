package lims.api.pr.service;

import lims.api.pr.vo.RePrintLabelApprVO;

import java.util.List;

public interface RePrintLabelApprService {
    // 라벨재출력승인 조회
    List<RePrintLabelApprVO> findAll(RePrintLabelApprVO param);
    // 라벨재출력승인
    int approve(RePrintLabelApprVO param);
    // 라벨재출력 반려
    int reject(RePrintLabelApprVO param);
}
