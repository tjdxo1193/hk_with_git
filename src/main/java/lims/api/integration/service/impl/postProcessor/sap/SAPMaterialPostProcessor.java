package lims.api.integration.service.impl.postProcessor.sap;

import lims.api.common.enums.DeleteType;
import lims.api.common.enums.PlantType;
import lims.api.common.enums.UseType;
import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.annotation.PostProcessorRevExceptionHandler;
import lims.api.integration.dao.SAPDao;
import lims.api.integration.dao.SAPMasterDao;
import lims.api.integration.dao.SAPPostProcessDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.enums.MaterialCharCode;
import lims.api.integration.enums.SAPPItemType;
import lims.api.integration.service.impl.postProcessor.PostProcessor;
import lims.api.integration.vo.SAPMaterialVO;
import lims.api.integration.vo.SAPPostProcessVO;
import lims.api.ms.enums.ItemManage;
import lims.api.ms.enums.SpecProgress;
import lims.api.util.process.SimpleSaveProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class SAPMaterialPostProcessor implements PostProcessor {

    private final SAPDao sapDao;
    private final SAPMasterDao masterDao;
    private final SAPPostProcessDao postProcessDao;

    @Override
    @PostProcessorRevExceptionHandler
    public synchronized void execute(RevStateful rev) {
        int latestDegree = rev.getDegree();
        SAPMaterialVO differentVO = new SAPMaterialVO();
        saveMaterialMaster(latestDegree, differentVO);
        updateDifferentDataToPItem(differentVO);
    }

    private void saveMaterialMaster(Integer degree, SAPMaterialVO differentVO) {
        int count = 0;

        count += new SimpleSaveProcess<SAPMaterialVO.Mara>().forEachSave(
                sapDao.findMaraAllByDegree(degree),
                masterDao.findMaterialMara(),
                masterDao::createMaterialMara,
                masterDao::updateMaterialMara,
                mara -> differentVO.getMara().add(mara)
        ).getTotalCount();

        count += new SimpleSaveProcess<SAPMaterialVO.Marc>().forEachSave(
                sapDao.findMarcAllByDegree(degree),
                masterDao.findMaterialMarc(),
                masterDao::createMaterialMarc,
                masterDao::updateMaterialMarc,
                marc -> differentVO.getMarc().add(marc)
        ).getTotalCount();

        count += new SimpleSaveProcess<SAPMaterialVO.Mvke>().forEachSave(
                sapDao.findMvkeAllByDegree(degree),
                masterDao.findMaterialMvke(),
                masterDao::createMaterialMvke,
                masterDao::updateMaterialMvke,
                mvke -> differentVO.getMvke().add(mvke)
        ).getTotalCount();

        count += new SimpleSaveProcess<SAPMaterialVO.Zmdv>().forEachSave(
                sapDao.findZmdvAllByDegree(degree),
                masterDao.findMaterialZmdv(),
                masterDao::createMaterialZmdv,
                masterDao::updateMaterialZmdv,
                zmdv -> differentVO.getZmdv().add(zmdv)
        ).getTotalCount();

        count += new SimpleSaveProcess<SAPMaterialVO.Makt>().forEachSave(
                sapDao.findMaktAllByDegree(degree),
                masterDao.findMaterialMakt(),
                masterDao::createMaterialMakt,
                masterDao::updateMaterialMakt,
                makt -> differentVO.getMakt().add(makt)
        ).getTotalCount();

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }
    }

    private void updateDifferentDataToPItem(SAPMaterialVO differentVO) {
        Set<String> changedMaterialCodes = Stream.of(
                        differentVO.getMara().stream().map(SAPMaterialVO.Mara::getMatnr).collect(Collectors.toList()),
                        differentVO.getMarc().stream().map(SAPMaterialVO.Marc::getMatnr).collect(Collectors.toList()),
                        differentVO.getMvke().stream().map(SAPMaterialVO.Mvke::getMatnr).collect(Collectors.toList()),
                        differentVO.getZmdv().stream().map(SAPMaterialVO.Zmdv::getMatnr).collect(Collectors.toList()),
                        differentVO.getMakt().stream().map(SAPMaterialVO.Makt::getMatnr).collect(Collectors.toList())
                )
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        List<SAPMaterialVO.Mara> latestMara = masterDao.findMaterialMara();
        List<SAPMaterialVO.Marc> latestMarc = masterDao.findMaterialMarc();
        Set<SAPPostProcessVO.Material.PItemKey> pitemKeys = getChangedPItemKeyForUpdate(latestMarc, changedMaterialCodes);

        SAPPostProcessVO.Material.PItemKey pItemKeyWithCurrentPItemVersion;
        Integer nextPItemVersion;
        boolean noExistsPItem;
        int pitemNewVersion;
        String pitmStatusCode;
        String specStatusCode;
        boolean isTempSaveStatusOfPItem;

        /**
         * TODO
         *  품목 임시저장 -> 업데이트
         *  품목 승인완료 -> 품목 버전 증가(상태값은 승인완료 고정)
         *                  -> 1. 규격서 INSERT
         *                  -> 2. 규격서 승인완료 > 버전업
         *                  -> 3. 규격서 승인요청 -> 버전업하고 기존거 취소
         *                  ->          임시저장 -> 기존 품목의 SAP 연계 필드들을 새로 연계받은 데이터로 업데이트
         */
        for (SAPPostProcessVO.Material.PItemKey pitemKey : pitemKeys) {

            noExistsPItem = !postProcessDao.existsPItem(pitemKey);
            if (noExistsPItem) {
                pitemNewVersion = postProcessDao.nextVersionOfPItem(pitemKey);
                createFirstVersionOfPItem(pitemNewVersion, latestMarc, pitemKey);
                createFirstVersionOfPItemSpec(pitemNewVersion, latestMara, pitemKey);
                continue;
            }

            pItemKeyWithCurrentPItemVersion = SAPPostProcessVO.Material.PItemKey.builder()
                    .plntCd(pitemKey.getPlntCd())
                    .pitmCd(pitemKey.getPitmCd())
                    .version(postProcessDao.currentVersionOfPItem(pitemKey))
                    .build();
            pitmStatusCode = postProcessDao.currentFinalVersionStatusCodeOfPItem(pItemKeyWithCurrentPItemVersion);
            specStatusCode = postProcessDao.currentFinalVersionStatusCodeOfSpec(pItemKeyWithCurrentPItemVersion);

            isTempSaveStatusOfPItem = ItemManage.TEMP_SAVE.equals(pitmStatusCode);
            if (isTempSaveStatusOfPItem) {
                updateInactiveOfCurrentPItem(latestMarc, pItemKeyWithCurrentPItemVersion, SpecProgress.APPROVED);

                nextPItemVersion = postProcessDao.nextVersionOfPItem(pitemKey);
                versionUpOfPItem(nextPItemVersion, latestMarc, pitemKey);
                continue;
            }

            boolean isApprovedPItm = ItemManage.APRROVED.equals(pitmStatusCode);
            if (isApprovedPItm) {
                if (SpecProgress.TEMPORARY_STORAGE.equals(specStatusCode)) {
                    updateInterfaceDataOfCurrentPItem(latestMarc, pItemKeyWithCurrentPItemVersion);
                    continue;
                }

                updateInactiveOfCurrentPItem(latestMarc, pItemKeyWithCurrentPItemVersion, SpecProgress.APPROVED);

                nextPItemVersion = postProcessDao.nextVersionOfPItem(pitemKey);
                versionUpOfPItem(nextPItemVersion, latestMarc, pitemKey);

                if (SpecProgress.APPROVAL_REQUEST.equals(specStatusCode)) {
                    updateInactiveOfCurrentPItemSpec(pItemKeyWithCurrentPItemVersion, SpecProgress.SPEC_REMOVE);
                    versionUpOfPItemSpec(nextPItemVersion, pitemKey, latestMara);
                    continue;
                }

                if (SpecProgress.APPROVED.equals(specStatusCode)) {
                    updateInactiveOfCurrentPItemSpec(pItemKeyWithCurrentPItemVersion, SpecProgress.APPROVED);
                    versionUpOfPItemSpec(nextPItemVersion, pitemKey, latestMara);
                }
            }

        }
    }

    private Set<SAPPostProcessVO.Material.PItemKey> getChangedPItemKeyForUpdate(List<SAPMaterialVO.Marc> latestMarc, Set<String> changedMaterialCodes) {
        return latestMarc.stream()
                .filter(marc -> changedMaterialCodes.contains(marc.getMatnr()))
                .map(marc -> SAPPostProcessVO.Material.PItemKey.builder()
                        .plntCd(marc.getWerks())
                        .pitmCd(marc.getMatnr())
                        .build()
                )
                .collect(Collectors.toSet());
    }

    private void createFirstVersionOfPItem(Integer newVersion, List<SAPMaterialVO.Marc> latestMarc, SAPPostProcessVO.Material.PItemKey pitemKey) {
        createNextVersionOfPItem(newVersion, latestMarc, pitemKey, SpecProgress.TEMPORARY_STORAGE);
    }

    private void versionUpOfPItem(Integer newVersion, List<SAPMaterialVO.Marc> latestMarc, SAPPostProcessVO.Material.PItemKey pitemKey) {
        createNextVersionOfPItem(newVersion, latestMarc, pitemKey, SpecProgress.APPROVED);
    }

    private void createNextVersionOfPItem(Integer newVersion, List<SAPMaterialVO.Marc> latestMarc, SAPPostProcessVO.Material.PItemKey pitemKey, SpecProgress progress) {
        SAPPostProcessVO.Material.PItem pitem = SAPPostProcessVO.Material.PItem.builder()
                .plntCd(pitemKey.getPlntCd())
                .pitmCd(pitemKey.getPitmCd())
                .pitmVer(newVersion)
                .specProcCd(progress)
                .rvsCtt("자재마스터 신규 등록")
                .delYn(UseType.N)
                .useVerYn(UseType.Y)
                .crtIp(PlantType.SYSTEM.getIp())
                .crtUid(PlantType.SYSTEM.getUid())
                .udtIp(PlantType.SYSTEM.getIp())
                .udtUid(PlantType.SYSTEM.getUid())
                .build();
        postProcessDao.createPItem(pitem);

        SAPPostProcessVO.Material.PItemInfo pitemInfo = new SAPPostProcessVO.Material.PItemInfo(pitemKey, newVersion);
        SAPMaterialVO.Marc currentMatchedMarc = getMarcByPItemKey(latestMarc, pitemKey);
        pitemInfo.setPItemTypByMRPCode(currentMatchedMarc.getDispo());
        postProcessDao.createPItemInfo(pitemInfo);

        SAPPostProcessVO.Material.PItemInfoSap pitemInfoSap = createPItemInfoSAPByCreateAndUpdate(pitemKey.getPlntCd(), pitemKey.getPitmCd(), newVersion);
        postProcessDao.createPItemInfoSap(pitemInfoSap);
    }

    private void updateInactiveOfCurrentPItem(List<SAPMaterialVO.Marc> latestMarc, SAPPostProcessVO.Material.PItemKey pitemKey, SpecProgress specProgress) {
        SAPPostProcessVO.Material.PItem pitem = SAPPostProcessVO.Material.PItem.builder()
                .plntCd(pitemKey.getPlntCd())
                .pitmCd(pitemKey.getPitmCd())
                .pitmVer(pitemKey.getVersion())
                .specProcCd(specProgress)
                .useVerYn(UseType.N)
                .build();
        postProcessDao.updatePItem(pitem);
        updateInterfaceDataOfCurrentPItem(latestMarc, pitemKey);
    }

    private void updateInterfaceDataOfCurrentPItem(List<SAPMaterialVO.Marc> latestMarc, SAPPostProcessVO.Material.PItemKey pitemKey) {
        SAPPostProcessVO.Material.PItemInfo pitemInfo = new SAPPostProcessVO.Material.PItemInfo(pitemKey);
        SAPMaterialVO.Marc currentMatchedMarc = getMarcByPItemKey(latestMarc, pitemKey);
        pitemInfo.setPItemTypByMRPCode(currentMatchedMarc.getDispo());
        postProcessDao.updatePItemInfo(pitemInfo);

        SAPPostProcessVO.Material.PItemInfoSap pitemInfoSap = createPItemInfoSAPByCreateAndUpdate(pitemKey.getPlntCd(), pitemKey.getPitmCd(), pitemKey.getVersion());
        postProcessDao.updatePItemInfoSap(pitemInfoSap);
    }

    private void createFirstVersionOfPItemSpec(Integer nextVersion, List<SAPMaterialVO.Mara> latestMara, SAPPostProcessVO.Material.PItemKey pitemKey) {
        SAPMaterialVO.Mara material = findMaterialByMaterialCode(pitemKey, latestMara);

        Integer alreadyApprovedPackageTestSpecIdxOrNull = null;
        if (isFinishOrPackagingMaterial(material)) {
            alreadyApprovedPackageTestSpecIdxOrNull = getAlreadyApprovedPackageSpecIdxOrNull(material);
        }

        Integer pitmSpecIdx = postProcessDao.nextPItemSpecIdxOfSpec(pitemKey);

        SAPPostProcessVO.Material.PItemSpec newSpec = SAPPostProcessVO.Material.PItemSpec.builder()
                .plntCd(pitemKey.getPlntCd())
                .pitmCd(pitemKey.getPitmCd())
                .pitmSpecIdx(pitmSpecIdx)
                .nextVersion(nextVersion)
                .useVerYn(UseType.N)
                .specProcCd(SpecProgress.TEMPORARY_STORAGE)
                .aitmSpecIdx(alreadyApprovedPackageTestSpecIdxOrNull)
                .aitmSpecIdxIsNull(isSemiMaterial(material) ? UseType.Y : UseType.N)
                .build();
        postProcessDao.createNewPItemSpec(newSpec);
    }

    private void updateInactiveOfCurrentPItemSpec(SAPPostProcessVO.Material.PItemKey pitemKey, SpecProgress specProgress) {
        SAPPostProcessVO.Material.PItemSpec removeSpec = SAPPostProcessVO.Material.PItemSpec.builder()
                .plntCd(pitemKey.getPlntCd())
                .pitmCd(pitemKey.getPitmCd())
                .currentVersion(pitemKey.getVersion())
                .specProcCd(specProgress)
                .useVerYn(UseType.N)
                .build();
        postProcessDao.updateStatusOfPItemSpec(removeSpec);
    }

    private void versionUpOfPItemSpec(Integer nextPItemVersion, SAPPostProcessVO.Material.PItemKey pitemKey, List<SAPMaterialVO.Mara> latestMara) {
        /**
         * TODO
         *  ○ 일반 품목 -> 기존 규격 IDX를 next에 넣어줌
         *  ○ 포장재    -> SAP 계층 코드에 맞는 최신버전이고 승인완료된 규격 IDX를 가져와야 한다. (포장 시험 테이블에 있는)
         *  ○ 반제품    -> 규격 IDX는 null
         */
        Integer currentVersion = postProcessDao.currentVersionOfSpec(pitemKey);
        Integer nextSpecIdx = postProcessDao.nextPItemSpecIdxOfSpec(pitemKey);

        SAPMaterialVO.Mara material = findMaterialByMaterialCode(pitemKey, latestMara);

        Integer alreadyApprovedPackageTestSpecIdxOrNull = null;
        if (isFinishOrPackagingMaterial(material)) {
            alreadyApprovedPackageTestSpecIdxOrNull = getAlreadyApprovedPackageSpecIdxOrNull(material);
        }

        SAPPostProcessVO.Material.PItemSpec newSpec = SAPPostProcessVO.Material.PItemSpec.builder()
                .plntCd(pitemKey.getPlntCd())
                .pitmCd(pitemKey.getPitmCd())
                .pitmSpecIdx(nextSpecIdx)
                .currentVersion(currentVersion)
                .aitmSpecIdx(alreadyApprovedPackageTestSpecIdxOrNull)
                .aitmSpecIdxIsNull(isSemiMaterial(material) ? UseType.Y : UseType.N)
                .nextVersion(nextPItemVersion)
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

    private Integer getAlreadyApprovedPackageSpecIdxOrNull(SAPMaterialVO.Mara finishOrPackagingMaterial) {
        SAPPostProcessVO.Material.PItemSpec packageTestCondition = SAPPostProcessVO.Material.PItemSpec.builder()
                .sapPrdha(finishOrPackagingMaterial.getPrdha())
                .specProcCd(SpecProgress.APPROVED)
                .build();
        return postProcessDao.findLatestApprovedPackageTest(packageTestCondition);
    }

    private SAPMaterialVO.Mara findMaterialByMaterialCode(SAPPostProcessVO.Material.PItemKey pitemKey, List<SAPMaterialVO.Mara> latestMara) {
        return latestMara.stream()
                .filter(mara -> mara.getMatnr().equals(pitemKey.getPitmCd()))
                .findAny()
                .orElse(null);
    }

    private boolean isFinishOrPackagingMaterial(SAPMaterialVO.Mara mara) {
        return mara != null && (SAPPItemType.isFinished(mara.getMatnr()) || SAPPItemType.isPackaging(mara.getMatnr()));
    }

    private boolean isSemiMaterial(SAPMaterialVO.Mara mara) {
        return mara != null && SAPPItemType.isSemi(mara.getMatnr());
    }

    private SAPMaterialVO.Marc getMarcByPItemKey(List<SAPMaterialVO.Marc> list, SAPPostProcessVO.Material.PItemKey itemKey) {
        return list.stream().filter(marc -> {
            SAPPostProcessVO.Material.PItemKey compare = SAPPostProcessVO.Material.PItemKey.builder()
                    .plntCd(marc.getWerks())
                    .pitmCd(marc.getMatnr())
                    .build();
            return itemKey.equals(compare);
        }).findAny().orElse(new SAPMaterialVO.Marc());
    }

    private SAPPostProcessVO.Material.PItemInfoSap createPItemInfoSAPByCreateAndUpdate(String plantCode, String pitmCode, Integer version) {
        Map<MaterialCharCode, String> zmdvMap = getZmdvMapByMaterialCode(pitmCode);
        return SAPPostProcessVO.Material.PItemInfoSap.builder()
                .plntCd(plantCode)
                .pitmCd(pitmCode)
                .pitmVer(version)
                .brdAbbr(zmdvMap.get(MaterialCharCode.BRD_ABBR))
                .etrCtnQty(zmdvMap.get(MaterialCharCode.ETR_CTN_QTY))
                .nbr(zmdvMap.get(MaterialCharCode.NBR))
                .ftnYn(zmdvMap.get(MaterialCharCode.FTN_YN))
                .pcs01(zmdvMap.get(MaterialCharCode.PCS01))
                .pcs02(zmdvMap.get(MaterialCharCode.PCS02))
                .pcs03(zmdvMap.get(MaterialCharCode.PCS03))
                .otcPrd(zmdvMap.get(MaterialCharCode.OTC_PRD))
                .dmsEptYn(zmdvMap.get(MaterialCharCode.DMS_EPT_YN))
                .pearDiv(zmdvMap.get(MaterialCharCode.PEAR_DIV))
                .mkrVol(zmdvMap.get(MaterialCharCode.MKR_VOL))
                .mkrVolUnit(zmdvMap.get(MaterialCharCode.MKR_VOL_UNIT))
                .etnAnsReq(zmdvMap.get(MaterialCharCode.ETN_ANS_REQ))
                .ctrptNo(zmdvMap.get(MaterialCharCode.CTRPT_NO))
                .dioYn(zmdvMap.get(MaterialCharCode.DIO_YN))
                .prbInYn(zmdvMap.get(MaterialCharCode.PRB_IN_YN))
                .prbFeYn(zmdvMap.get(MaterialCharCode.PRB_FE_YN))
                .pnxFeYn(zmdvMap.get(MaterialCharCode.PNX_FE_YN))
                .build();
    }

    private Map<MaterialCharCode, String> getZmdvMapByMaterialCode(String materialCode) {
        Map<MaterialCharCode, String> result = new HashMap<>();

        Map<String, SAPMaterialVO.Zmdv> zmdvByMaterialCodeSet = masterDao.findMaterialZmdvByMaterialCode(materialCode)
                .stream().collect(Collectors.toMap(SAPMaterialVO.Zmdv::getCharCode, o -> o, (oldValue, newValue) -> oldValue));

        MaterialCharCode[] codes = MaterialCharCode.values();
        SAPMaterialVO.Zmdv zmdvByMaterialCode;
        for (MaterialCharCode code : codes) {
            if (!zmdvByMaterialCodeSet.containsKey(code.getValue())) {
                continue;
            }

            zmdvByMaterialCode = zmdvByMaterialCodeSet.get(code.getValue());

            if ("CHAR".equals(zmdvByMaterialCode.getCharDataTyp())) {
                result.put(code, zmdvByMaterialCode.getCharValChar());
            }
            if ("NUM".equals(zmdvByMaterialCode.getCharDataTyp())) {
                result.put(code, zmdvByMaterialCode.getCharValNum());
            }
        }
        return result;
    }

}