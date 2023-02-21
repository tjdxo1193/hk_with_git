package lims.api.integration.service.impl.postProcessor.sap;

import lims.api.common.enums.DeleteType;
import lims.api.common.enums.PlantType;
import lims.api.common.enums.UseType;
import lims.api.integration.dao.SAPMasterDao;
import lims.api.integration.dao.SAPPostProcessDao;
import lims.api.integration.enums.SAPPItemType;
import lims.api.integration.vo.SAPMaterialVO;
import lims.api.integration.vo.SAPPostProcessVO;
import lims.api.ms.enums.SpecProgress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SAPMaterialSpecStrategy {

    private final SAPMasterDao masterDao;
    private final SAPPostProcessDao postProcessDao;

    public void updateInactiveOfCurrentPItemSpec(String plantCode, String materialCode, Integer version, SpecProgress specProgress) {
        SAPPostProcessVO.Material.PItemSpec removeSpec = SAPPostProcessVO.Material.PItemSpec.builder()
                .plntCd(plantCode)
                .pitmCd(materialCode)
                .currentVersion(version)
                .specProcCd(specProgress)
                .useVerYn(UseType.N)
                .build();
        postProcessDao.updateStatusOfPItemSpec(removeSpec);
    }

    public void versionUpOfPItemSpec(String plantCode, String materialCode, Integer pitemVersion, SAPMaterialVO.Mara material, SAPMaterialVO.Marc marc) {
        /**
         * TODO
         *  ○ 기본                                                -> 이전 규격서의 버전을 가져옴
         *  ○ 완제품, 포장재                                        -> SAP 계층 코드에 맞는 최신버전이고 승인완료된 규격 IDX를 가져와야 한다. (포장 시험 테이블에 있는)
         *  ○ ELN 기준 반제품(Bulk, Base 한정)이고 LabNo가 변경됐다면   -> 규격 IDX는 null
         */
        SAPPostProcessVO.Material.PItemKey pitemKey = SAPPostProcessVO.Material.PItemKey.builder()
                .plntCd(plantCode)
                .pitmCd(materialCode)
                .version(pitemVersion)
                .build();

        Integer nextSpecIdx = postProcessDao.nextPItemSpecIdxOfSpec(pitemKey);

        SAPPostProcessVO.Material.PItemSpec currentVersionPItemSpec = postProcessDao.currentVersionOfSpec(pitemKey);
        Integer alreadyApprovedPackageTestSpecIdxOrNull = currentVersionPItemSpec.getAitmSpecIdx();

        if (isFinishOrPackagingMaterial(material)) {
            alreadyApprovedPackageTestSpecIdxOrNull = getAlreadyApprovedPackageSpecIdxOrNull(plantCode, material);
        } else if (shouldBeSpecVersionIdxIsNull(material)) {
            alreadyApprovedPackageTestSpecIdxOrNull = null;
        } else if (material.isChangedELNStandard()) {
            alreadyApprovedPackageTestSpecIdxOrNull = null;
        }

        SAPPostProcessVO.Material.PItemSpec newSpec = SAPPostProcessVO.Material.PItemSpec.builder()
                .plntCd(pitemKey.getPlntCd())
                .pitmCd(pitemKey.getPitmCd())
                .pitmSpecIdx(nextSpecIdx)
                .currentVersion(currentVersionPItemSpec.getPitmVer())
                .aitmSpecIdx(alreadyApprovedPackageTestSpecIdxOrNull)
                .pitmVer(pitemVersion)
                .specProcCd(SpecProgress.TEMPORARY_STORAGE)
                .delYn(DeleteType.N)
                .useVerYn(UseType.N)
                .crtIp(PlantType.SYSTEM.getIp())
                .crtUid(PlantType.SYSTEM.getUid())
                .udtIp(PlantType.SYSTEM.getIp())
                .udtUid(PlantType.SYSTEM.getUid())
                .build();
        postProcessDao.createNextPItemSpec(newSpec);
    }

    public SAPMaterialVO.Mara getMaterial(SAPPostProcessVO.Material.PItemKey pitemKey) {
        return masterDao.findMaterialMaraById(pitemKey.getPitmCd());
    }

    public SAPMaterialVO.Marc getMarc(SAPPostProcessVO.Material.PItemKey pitemKey) {
        SAPMaterialVO.Marc marcParam = new SAPMaterialVO.Marc();
        marcParam.setWerks(pitemKey.getPlntCd());
        marcParam.setMatnr(pitemKey.getPitmCd());
        return masterDao.findMaterialMarcById(marcParam);
    }

    public Integer getAlreadyApprovedPackageSpecIdxOrNull(String plantCode, SAPMaterialVO.Mara finishOrPackagingMaterial) {
        SAPPostProcessVO.Material.PItemSpec packageTestCondition = SAPPostProcessVO.Material.PItemSpec.builder()
                .plntCd(plantCode)
                .sapPrdha(finishOrPackagingMaterial.getPrdha())
                .specProcCd(SpecProgress.APPROVED)
                .build();
        return postProcessDao.findLatestApprovedPackageTest(packageTestCondition);
    }

    public boolean isFinishOrPackagingMaterial(SAPMaterialVO.Mara mara) {
        return mara != null && (SAPPItemType.isFinished(mara.getMatnr()) || SAPPItemType.isPackaging(mara.getMatnr()));
    }

    public boolean shouldBeSpecVersionIdxIsNull(SAPMaterialVO.Mara material) {
        return material.isChangedLabNo() && isSemiMaterial(material);
    }

    private boolean isSemiMaterial(SAPMaterialVO.Mara mara) {
        return mara != null && SAPPItemType.isSemi(mara.getMtart());
    }

    public void updateToNullPItemSpecVersion(SAPPostProcessVO.Material.PItemKey pitemKey) {
        postProcessDao.updateToNullPItemSpecVersion(pitemKey);
    }

}