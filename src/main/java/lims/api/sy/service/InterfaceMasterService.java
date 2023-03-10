package lims.api.sy.service;

import lims.api.sy.vo.InterfaceMasterVO;

import java.util.List;

public interface InterfaceMasterService {

    List<InterfaceMasterVO> getMasters(InterfaceMasterVO param);

}