package lims.api.sy.service.impl;

import lims.api.sy.dao.NoticeDao;
import lims.api.sy.service.NoticeService;
import lims.api.sy.vo.NoticeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    @Override
    public List<NoticeVO> findAll(@Validated NoticeVO vo) {
        return noticeDao.findAll(vo);
    }
}