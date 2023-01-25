package lims.api.sy.service;

import lims.api.sy.vo.SystemCommonCodeManagementVO;

import java.util.List;

public interface SystemCommonCodeManagementService {
    List<SystemCommonCodeManagementVO> getSyCmCodeHir(SystemCommonCodeManagementVO request);

    List<SystemCommonCodeManagementVO> getSyCmCodeDtl(SystemCommonCodeManagementVO request);
}
