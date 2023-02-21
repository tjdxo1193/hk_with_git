package lims.api.pr.service;

import lims.api.pr.vo.PrintLabelVO;

import java.util.List;

public interface PrintLabelService {
    // 라벨출력 조회
    List<PrintLabelVO> findAll(PrintLabelVO param);
    // 라벨출력 프로세스
    int printLabel(List<PrintLabelVO> param);
}
