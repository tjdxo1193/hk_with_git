package lims.api.sy.service.impl;

import lims.api.sy.dao.SystemCommonCodeManagementDao;
import lims.api.sy.service.SystemCommonCodeManagementService;
import lims.api.sy.vo.SystemCommonCodeManagementVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemCommonCodeManagementServiceImpl implements SystemCommonCodeManagementService {

    private final SystemCommonCodeManagementDao dao;

    @Override
    public List<SystemCommonCodeManagementVO> getSyCmCodeHir(SystemCommonCodeManagementVO request) {
        return dao.getSyCmCodeHir(request);
    }

    @Override
    public List<SystemCommonCodeManagementVO> getSyCmCodeDtl(SystemCommonCodeManagementVO request) {
        return dao.getSyCmCodeDtl(request);
    }
}
