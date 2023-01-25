package lims.api.ms.service;

import lims.api.ms.vo.SpecApprVO;

import java.util.List;

public interface SpecApprService {

    List<SpecApprVO> getVersionList(SpecApprVO param);

    List<SpecApprVO> getAItemList(SpecApprVO param);

    void updateApproval(List<SpecApprVO> param);

    void updateReject(List<SpecApprVO> param);
}
