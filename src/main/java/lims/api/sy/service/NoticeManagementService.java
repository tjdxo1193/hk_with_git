package lims.api.sy.service;

import lims.api.sy.vo.NoticeManagementVO;

import java.util.List;

public interface NoticeManagementService {
    List<NoticeManagementVO> findAll(NoticeManagementVO vo);

    NoticeManagementVO update(NoticeManagementVO request);

    NoticeManagementVO create(NoticeManagementVO request);
}