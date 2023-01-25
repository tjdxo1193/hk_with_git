package lims.api.sy.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.sy.dao.AuthManageDao;
import lims.api.sy.service.AuthManageService;
import lims.api.sy.vo.AuthManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthManageServiceImpl implements AuthManageService {
    private final AuthManageDao dao;

    @Override
    public List<AuthManageVO> findAllAth(AuthManageVO param) {
        return dao.findAll(param);
    }

    @Override
    public List<AuthManageVO> findAthGp(AuthManageVO param) {
        return dao.findAthGp(param);
    }

    @Override
    public List<AuthManageVO> findNotAthGp(AuthManageVO param) {
        return dao.findNotAthGp(param);
    }

    @Override
    public void insert(AuthManageVO param, String plntCd) {
        int cnt = 0;
        for (AuthManageVO item : param.getAddedRowItems()) {
            item.setPlntCd(plntCd);
            cnt += dao.insert(item);
        }
        if (cnt == 0) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void update(AuthManageVO param, String plntCd) {
        int cnt = 0;

        for (AuthManageVO item : param.getEditedRowItems()) {
            item.setPlntCd(plntCd);
            cnt += dao.update(item);
        }
        if (cnt == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void insertAthGp(AuthManageVO param, String plntCd) {
        int cnt = 0;
        List<AuthManageVO> vos = dao.findAthGp(param);

        for (AuthManageVO item : param.getAddedRowItems()) {
            if (vos.contains(item)) {
                continue;
            }
            item.setPlntCd(plntCd);
            cnt += dao.insertAthGp(item);
        }
        if (cnt == 0) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void deleteAthGp(AuthManageVO param, String plntCd) {
        int cnt = 0;

        for (AuthManageVO item : param.getRemovedRowItems()) {
            item.setPlntCd(plntCd);
            cnt += dao.deleteAthGp(item);
        }
        if (cnt == 0) {
            throw new NoDeletedDataException();
        }
    }
}