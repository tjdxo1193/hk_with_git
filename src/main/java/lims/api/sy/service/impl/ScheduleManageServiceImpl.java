package lims.api.sy.service.impl;

import lims.api.sy.dao.ScheduleManageDao;
import lims.api.sy.service.ScheduleManageService;
import lims.api.sy.vo.ScheduleManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleManageServiceImpl implements ScheduleManageService {

    private final ScheduleManageDao dao;

    @Override
    public List<ScheduleManageVO> getList(ScheduleManageVO param) {
        return dao.getList(param);
    }
}
