package lims.api.sy.service.impl;

import lims.api.sy.dao.NoticeManagementDao;
import lims.api.sy.service.NoticeManagementService;
import lims.api.sy.vo.NoticeManagementVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeManagementServiceImpl implements NoticeManagementService {

    private final NoticeManagementDao noticeManagementDao;

    @Override
    public List<NoticeManagementVO> findAll(NoticeManagementVO vo) {
        return noticeManagementDao.findAll(vo);
    }

    @Override
    public NoticeManagementVO update(NoticeManagementVO vo) {
        if (!vo.getPopYn().isEmpty() && vo.getPopYn().equals("Y")){
            noticeManagementDao.updateOfPopYn(vo);
        }
        noticeManagementDao.update(vo);
        return vo;
    }

    @Override
    public NoticeManagementVO create(NoticeManagementVO vo) {
        if (!vo.getPopYn().isEmpty() && vo.getPopYn().equals("Y")){
            noticeManagementDao.updateOfPopYn(vo);
        }
        noticeManagementDao.create(vo);
        return vo;
    }
}