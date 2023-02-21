package lims.api.ms.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoDeletedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.ms.dao.VendorManageDao;
import lims.api.ms.service.VendorManageService;
import lims.api.ms.vo.VendorVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorManageServiceImpl implements VendorManageService {

    private final VendorManageDao dao;

    @Override
    public List<VendorVO> findAll(VendorVO param) {
        return dao.findAll(param);
    }

    @Override
    public  void create(VendorVO param) {
        int result = dao.create(param);

        if (result == 0) {
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void update(VendorVO param) {
        int result = dao.update(param);

        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void delete(VendorVO param) {
        int result = dao.delete(param);

        if (result == 0) {
            throw new NoDeletedDataException();
        }
    }
}
