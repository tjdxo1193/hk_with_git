package lims.api.integration.service.impl;

import lims.api.integration.dao.InterfaceInfoDao;
import lims.api.integration.service.InterfaceInfoService;
import lims.api.integration.vo.IfInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterfaceInfoServiceImpl implements InterfaceInfoService {

    private final InterfaceInfoDao infoDao;

    @Override
    public synchronized Integer createInfo(IfInfoVO data) {
        Integer idx = infoDao.nextId();
        data.setIdx(idx);
        infoDao.createInfo(data);
        return idx;
    }

    @Override
    public int updateStatusInfo(IfInfoVO data) {
        return infoDao.updateStatusInfo(data);
    }
}