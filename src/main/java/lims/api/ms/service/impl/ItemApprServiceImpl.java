package lims.api.ms.service.impl;

import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.ms.dao.ItemApprDao;
import lims.api.ms.enums.PItemType;
import lims.api.ms.enums.SpecProgress;
import lims.api.ms.service.ItemApprService;
import lims.api.ms.vo.ItemApprSpecAitmVO;
import lims.api.ms.vo.ItemApprSpecVO;
import lims.api.ms.vo.ItemManageVO;
import lims.api.ms.vo.SpecManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemApprServiceImpl implements ItemApprService {
    private final ItemApprDao dao;
    private final ApproveService approveService;

    // 승인 대기 항목 조회
    @Override
    public List<ItemManageVO> find(ItemManageVO param) {
        param.setSpecProcCd(SpecProgress.APPROVAL_REQUEST.getCode());
        return dao.find(param);
    }

    @Override
    public void approve(List<ItemManageVO> params) {
        int result = 0;
        for (ItemManageVO param : params) {
            if (param.getPitmMstAprIdx() != null && param.getPitmVer() < 2) {   // 품목 최초 승인인 경우
                ApproveVO approveInfo = setApproveVO(param);
                dao.createQmPitmSpec(setNewSpec(param));
                approveService.approve(approveInfo.getAprIdx());
            } else {        // 품목 개정인 경우
                ItemManageVO oldSpec = getOldSpec(param);
                String specProcCd = oldSpec.getSpecProcCd();
                Integer pitmSpecIdx = oldSpec.getPitmSpecIdx();
                if (specProcCd.equals(SpecProgress.APPROVED.getCode())) {
                    if (isWrap(param)) {
                        param.setAitmSpecIdx(getAitmSpecIdxBySapPrdha(param));
                    } else {
                        Integer oldAitmSpecIdx = oldSpec.getAitmSpecIdx();
                        param.setAitmSpecIdx(oldAitmSpecIdx);
                        int newAitmSpecIdx = createAitmSpec(param);
                        param.setAitmSpecIdx(newAitmSpecIdx);
                        oldSpecToNewSpec(param, oldAitmSpecIdx);
                    }
                    updateSpec(oldSpec, pitmSpecIdx);
                    dao.createQmPitmSpec(setNewSpec(param));
                } else if (specProcCd.equals(SpecProgress.TEMPORARY_STORAGE.getCode())) {
                    oldSpec.setPitmVer(param.getPitmVer());
                    oldSpec.setUseVerYn('N');
                    updateSpecPitmVer(param, pitmSpecIdx);
                }
            }
            updateApprovePitm(param);
        }
    }

    @Override
    public void reject(List<ItemManageVO> params) {
        int result = 0;
        for (ItemManageVO param : params) {
            param.setSpecProcCd(SpecProgress.APPROVAL_REJECTION.getCode());
            result += dao.reject(param);
        }
        if (result != params.size()) {
            throw new NoUpdatedDataException();
        }
    }

    private ItemManageVO setNewSpec(ItemManageVO param) {
        ItemManageVO newSpec = new ItemManageVO();
        if(param.getPitmVer() == 1){
            param.setAitmSpecIdx(getAitmSpecIdxBySapPrdha(param));
        }
        newSpec.setPlntCd(param.getPlntCd());
        newSpec.setAitmSpecIdx(param.getAitmSpecIdx());
        newSpec.setPitmCd(param.getPitmCd());
        newSpec.setPitmVer(param.getPitmVer());
        newSpec.setUseVerYn('N');
        newSpec.setSpecProcCd(SpecProgress.TEMPORARY_STORAGE.getCode());
        return newSpec;
    }

    private void updateSpec(ItemManageVO param, Integer oldPitmSpecIdx) {
        ItemManageVO spec = new ItemManageVO();
        spec.setPlntCd(param.getPlntCd());
        spec.setPitmSpecIdx(oldPitmSpecIdx);
        spec.setUseVerYn('N');
        dao.updateSpec(spec);
    }

    private void updateSpecPitmVer(ItemManageVO param, Integer pitmSpecIdx) {
        ItemManageVO spec = new ItemManageVO();
        spec.setPlntCd(param.getPlntCd());
        spec.setPitmVer(param.getPitmVer());
        spec.setPitmSpecIdx(pitmSpecIdx);
        dao.updateSpecPitmVer(spec);
    }

    // 새로운 규격서에 이전 버전의 시험 항목 규격 옮기기
    private void oldSpecToNewSpec(ItemManageVO param, int oldAitmSpecIdx) {
        ItemApprSpecVO oldSpec = new ItemApprSpecVO();
        oldSpec.setPlntCd(param.getPlntCd());
        oldSpec.setAitmSpecIdx(oldAitmSpecIdx);
        List<ItemApprSpecAitmVO> oldSpecAitmList = dao.findSpecAitm(oldSpec);
        int result = 0;
        if (oldSpecAitmList != null) {
            for (ItemApprSpecAitmVO oldSpecAitm : oldSpecAitmList) {
                oldSpecAitm.setAitmSpecIdx(param.getAitmSpecIdx());
                result += dao.createQmPitmSpecAitm(oldSpecAitm);
            }
        }
        if (result == 0) {
            throw new NoCreatedDataException();
        }
    }

    private void updateApprovePitm(ItemManageVO param) {
        ItemManageVO pitm = new ItemManageVO();
        int pitmVer = param.getPitmVer();
        pitm.setPlntCd(param.getPlntCd());
        pitm.setPitmCd(param.getPitmCd());
        pitm.setPitmVer(pitmVer);
        pitm.setSpecProcCd(SpecProgress.APPROVED.getCode());
        pitm.setUseVerYn('Y');
        int result = 0;
        result += dao.approve(pitm);
        if (pitmVer > 1) {
            pitm.setPitmVer(--pitmVer);
            pitm.setUseVerYn('N');
            result += dao.updatePitm(pitm);
        }
        if (result == 0) {
            throw new NoUpdatedDataException();
        }
    }

    private int createAitmSpec(ItemManageVO param) {
        ItemManageVO newVO = dao.findNewAitmSpecIdx(param);
        param.setAitmSpecIdx(newVO.getAitmSpecIdx());
        param.setAitmSpecVer(newVO.getAitmSpecVer());
        dao.createQmPitmAitmSpec(param);
        return param.getAitmSpecIdx();
    }


    private Integer getAitmSpecIdxBySapPrdha(ItemManageVO param) {
        ItemManageVO sapItem = new ItemManageVO();
        sapItem.setPlntCd(param.getPlntCd());
        sapItem.setSapPrdha(param.getSapPrdha());
        sapItem.setSpecProcCd(SpecProgress.APPROVED.getCode());
        Integer newAitmSpecIdx = dao.findAitmSpecIdxBySapPrdha(sapItem).getNewAitmSpecIdx();
        if (newAitmSpecIdx > 0) {
            return newAitmSpecIdx;
        } else {
            return null;
        }
    }

    private ItemManageVO getOldSpec(ItemManageVO param) {
        ItemManageVO spec = new ItemManageVO();
        spec.setPlntCd(param.getPlntCd());
        spec.setPitmCd(param.getPitmCd());
        return dao.findSpecByPitmCd(param);
    }

    private boolean isWrap(ItemManageVO param) {
        String pitmTyp = param.getPitmTyp();
        return pitmTyp.equals(PItemType.PACKAGING_MATERIAL.getCode())
                || pitmTyp.equals(PItemType.FINISHED_SET.getCode())
                || pitmTyp.equals(PItemType.FINISHED_SINGLE.getCode());
    }

    /* 공통 승인 관련 */
    // 공통 승인 객체 설정
    private ApproveVO setApproveVO(ItemManageVO param) {
        ApproveVO approveInfo = new ApproveVO();
        approveInfo.setPlntCd(param.getPlntCd());
        approveInfo.setAprIp(param.getAprIp());
        approveInfo.setAprUid(param.getAprUid());
        approveInfo.setAprRea(param.getAprRea());
        approveInfo.setAprIdx(param.getPitmMstAprIdx());
        return approveInfo;
    }
}
