package lims.api.sy.service;

import lims.api.sy.vo.NoticeVO;

import java.util.List;

public interface NoticeService {
    List<NoticeVO> findAll(NoticeVO vo);
}