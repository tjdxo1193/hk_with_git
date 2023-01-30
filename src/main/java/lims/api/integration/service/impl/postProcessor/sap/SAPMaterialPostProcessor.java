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
import org.apache.commons.lang3.StringUtils;
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

        List<SAPMaterialVO.Mara> maras = sapDao.findMaraAllByDegree(degree);
        count += new SimpleSaveProcess<SAPMaterialVO.Mara>().forEachSave(
                maras,
                masterDao.findMaterialMara(maras),
                masterDao::createMaterialMara,
                masterDao::updateMaterialMara,
                (mara, existsMara) -> {
                    if (!StringUtils.equals(mara.getZlabno(), existsMara.getZlabno())) {
                        mara.markingChangeLabNo();
                    }
                    differentVO.getMara().add(mara);
                }
        ).getTotalCount();

        List<SAPMaterialVO.Marc> marcs = sapDao.findMarcAllByDegree(degree);
        count += new SimpleSaveProcess<SAPMaterialVO.Marc>().forEachSave(
                marcs,
                masterDao.findMaterialMarc(marcs),
                masterDao::createMaterialMarc,
                masterDao::updateMaterialMarc,
                (marc, existsMara) -> differentVO.getMarc().add(marc)
        ).getTotalCount();

        List<SAPMaterialVO.Mvke> mvkes = sapDao.findMvkeAllByDegree(degree);
        count += new SimpleSaveProcess<SAPMaterialVO.Mvke>().forEachSave(
                mvkes,
                masterDao.findMaterialMvke(mvkes),
                masterDao::createMaterialMvke,
                masterDao::updateMaterialMvke,
                (mvke, existsMvke) -> differentVO.getMvke().add(mvke)
        ).getTotalCount();

        List<SAPMaterialVO.Zmdv> zmdvs = sapDao.findZmdvAllByDegree(degree);
        count += new SimpleSaveProcess<SAPMaterialVO.Zmdv>().forEachSave(
                zmdvs,
                masterDao.findMaterialZmdv(zmdvs),
                masterDao::createMaterialZmdv,
                masterDao::updateMaterialZmdv,
                (zmdv, existsZmdv) -> differentVO.getZmdv().add(zmdv)
        ).getTotalCount();

        List<SAPMaterialVO.Makt> makts = sapDao.findMaktAllByDegree(degree);
        count += new SimpleSaveProcess<SAPMaterialVO.Makt>().forEachSave(
                makts,
                masterDao.findMaterialMakt(makts),
                masterDao::createMaterialMakt,
                masterDao::updateMaterialMakt,
                (makt, existsMakt) -> differentVO.getMakt().add(makt)
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

        List<SAPMaterialVO.Mara> latestMara = masterDao.findMaterialMaraAll();
        Map<String, SAPMaterialVO.Mara> materialMap = toMaterialMapByMaterialCode(latestMara);

        List<SAPMaterialVO.Marc> latestMarc = masterDao.findMaterialMarcAll();
        
        Set<SAPPostProcessVO.Material.PItemKey> pitemKeys = getChangedPItemKeyForUpdate(latestMarc, changedMaterialCodes);

        SAPPostProcessVO.Material.PItemKey pItemKeyWithCurrentPItemVersion;
        SAPMaterialVO.Mara material;
        SAPMaterialVO.Marc matchedMarc;
        Integer nextPItemVersion;
        boolean noExistsPItem;
        int pitemNewVersion;
        String pitmStatusCode;
        String specStatusCode;

        /**
         * @implNote
         *  품목 최초생성 -> 새 품목 버전 생성 및 새 규격서 생성
         *  품목 임시저장 -> 기존 버전 비활성화 및 새 품목 버전 생성 (상태값 = 승인완료)
         *  품목 승인완료 &
         *      규격서 [임시저장, 검토반려]             -> 기존 품목의 SAP 연계 필드들을 새로 연계받은 데이터로 업데이트
         *      규격서 [검토요청, 승인요청, 승인반려]    -> 기존 버전 비활성화 및 새 품목 버전 생성 (상태값 = 승인완료)
         *                                             기존 규격서 비활성화 및 새 규격서 생성 (상태값 = 규격서 삭제)
         *      규격서 [승인완료]                     -> 기존 버전 비활성화 및 새 품목 버전 생성 (상태값 = 승인완료)
         *                                             기존 규격서 비활성화 및 새 규격서 생성 (상태값 = 승인완료)
         */
        for (SAPPostProcessVO.Material.PItemKey pitemKey : pitemKeys) {
            material = materialMap.get(pitemKey.getPitmCd());
            matchedMarc = getMarcByPItemKey(latestMarc, pitemKey);

            noExistsPItem = !postProcessDao.existsPItem(pitemKey);
            if (noExistsPItem) {
                pitemNewVersion = postProcessDao.nextVersionOfPItem(pitemKey);
                createFirstVersionOfPItem(pitemNewVersion, matchedMarc, pitemKey);
                createFirstVersionOfPItemSpec(pitemNewVersion, material, pitemKey);
                continue;
            }

            pItemKeyWithCurrentPItemVersion = SAPPostProcessVO.Material.PItemKey.builder()
                    .plntCd(pitemKey.getPlntCd())
                    .pitmCd(pitemKey.getPitmCd())
                    .version(postProcessDao.currentVersionOfPItem(pitemKey))
                    .build();
            pitmStatusCode = postProcessDao.currentFinalVersionStatusCodeOfPItem(pItemKeyWithCurrentPItemVersion);
            specStatusCode = postProcessDao.currentFinalVersionStatusCodeOfSpec(pItemKeyWithCurrentPItemVersion);

            if (ItemManage.TEMP_SAVE.equals(pitmStatusCode)) {
                updateInactiveOfCurrentPItem(matchedMarc, pItemKeyWithCurrentPItemVersion, SpecProgress.APPROVED);

                nextPItemVersion = postProcessDao.nextVersionOfPItem(pitemKey);
                versionUpOfPItem(nextPItemVersion, matchedMarc, pitemKey);
                continue;
            }

            if (ItemManage.APRROVED.equals(pitmStatusCode)) {

                if (SpecProgress.TEMPORARY_STORAGE.equals(specStatusCode) || SpecProgress.REVIEW_RETURN.equals(specStatusCode)) {
                    updateInterfaceDataOfCurrentPItem(matchedMarc, pItemKeyWithCurrentPItemVersion);
                    continue;
                }

                nextPItemVersion = postProcessDao.nextVersionOfPItem(pitemKey);

                updateInactiveOfCurrentPItem(matchedMarc, pItemKeyWithCurrentPItemVersion, SpecProgress.APPROVED);
                versionUpOfPItem(nextPItemVersion, matchedMarc, pitemKey);

                if (SpecProgress.APPROVAL_REQUEST.equals(specStatusCode) || SpecProgress.REQUEST_REVIEW.equals(specStatusCode) ||
                        SpecProgress.APPROVAL_REJECTION.equals(specStatusCode)) {
                    updateInactiveOfCurrentPItemSpec(pItemKeyWithCurrentPItemVersion, SpecProgress.SPEC_REMOVE);
                    versionUpOfPItemSpec(nextPItemVersion, pitemKey, material, matchedMarc);
                    continue;
                }

                if (SpecProgress.APPROVED.equals(specStatusCode)) {
                    updateInactiveOfCurrentPItemSpec(pItemKeyWithCurrentPItemVersion, SpecProgress.APPROVED);
                    versionUpOfPItemSpec(nextPItemVersion, pitemKey, material, matchedMarc);
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

    private void createFirstVersionOfPItem(Integer newVersion, SAPMaterialVO.Marc marc, SAPPostProcessVO.Material.PItemKey pitemKey) {
        createNextVersionOfPItem(newVersion, marc, pitemKey, SpecProgress.TEMPORARY_STORAGE);
    }

    private void versionUpOfPItem(Integer newVersion, SAPMaterialVO.Marc marc, SAPPostProcessVO.Material.PItemKey pitemKey) {
        createNextVersionOfPItem(newVersion, marc, pitemKey, SpecProgress.APPROVED);
    }

    private void createNextVersionOfPItem(Integer newVersion, SAPMaterialVO.Marc marc, SAPPostProcessVO.Material.PItemKey pitemKey, SpecProgress progress) {
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
        pitemInfo.setPItemTypByMRPCode(marc.getDispo());
        postProcessDao.createPItemInfo(pitemInfo);

        SAPPostProcessVO.Material.PItemInfoSap pitemInfoSap = createPItemInfoSAPByCreateAndUpdate(pitemKey.getPlntCd(), pitemKey.getPitmCd(), newVersion);
        postProcessDao.createPItemInfoSap(pitemInfoSap);
    }

    private void updateInactiveOfCurrentPItem(SAPMaterialVO.Marc marc, SAPPostProcessVO.Material.PItemKey pitemKey, SpecProgress specProgress) {
        SAPPostProcessVO.Material.PItem pitem = SAPPostProcessVO.Material.PItem.builder()
                .plntCd(pitemKey.getPlntCd())
                .pitmCd(pitemKey.getPitmCd())
                .pitmVer(pitemKey.getVersion())
                .specProcCd(specProgress)
                .useVerYn(UseType.N)
                .udtUid(PlantType.SYSTEM.getUid())
                .udtIp(PlantType.SYSTEM.getIp())
                .build();
        postProcessDao.updatePItem(pitem);
        updateInterfaceDataOfCurrentPItem(marc, pitemKey);
    }

    private void updateInterfaceDataOfCurrentPItem(SAPMaterialVO.Marc marc, SAPPostProcessVO.Material.PItemKey pitemKey) {
        SAPPostProcessVO.Material.PItemInfo pitemInfo = new SAPPostProcessVO.Material.PItemInfo(pitemKey);
        pitemInfo.setPItemTypByMRPCode(marc.getDispo());
        postProcessDao.updatePItemInfo(pitemInfo);

        SAPPostProcessVO.Material.PItemInfoSap pitemInfoSap = createPItemInfoSAPByCreateAndUpdate(pitemKey.getPlntCd(), pitemKey.getPitmCd(), pitemKey.getVersion());
        postProcessDao.updatePItemInfoSap(pitemInfoSap);
    }

    private void createFirstVersionOfPItemSpec(Integer nextVersion, SAPMaterialVO.Mara material, SAPPostProcessVO.Material.PItemKey pitemKey) {
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

    private void versionUpOfPItemSpec(Integer nextPItemVersion, SAPPostProcessVO.Material.PItemKey pitemKey, SAPMaterialVO.Mara material, SAPMaterialVO.Marc marc) {
        /**
         * TODO
         *  ○ 완제품, 포장재    -> SAP 계층 코드에 맞는 최신버전이고 승인완료된 규격 IDX를 가져와야 한다. (포장 시험 테이블에 있는)
         *  ○ ELN 기준 반제품(Bulk, Base 한정)    -> 규격 IDX는 null
         *  ○ 나머지 -> 규격서 버전을 새로 하나 생성하여 생성된 규격서 버전 IDX를 새로 생성한 규격서 정보에 넣어줌
         */
        SAPPostProcessVO.Material.PItemSpec currentVersionPItemSpec = postProcessDao.currentVersionOfSpec(pitemKey);
        Integer nextSpecIdx = postProcessDao.nextPItemSpecIdxOfSpec(pitemKey);

        Integer alreadyApprovedPackageTestSpecIdxOrNull;

        if (isFinishOrPackagingMaterial(material)) {
            alreadyApprovedPackageTestSpecIdxOrNull = getAlreadyApprovedPackageSpecIdxOrNull(material);
        } else if (isSemiMaterialByELN(material, marc)) {
            alreadyApprovedPackageTestSpecIdxOrNull = null;
        } else {
            /**
             * 완제품, 포장재, ELN기준 반제품(ELN기준의 반제품은 Bulk, Base로 한정)이 아니면 규격서의 버전을 새로 하나 생성한다.
             */
            SAPPostProcessVO.Material.PItemSpecVersion specVersion = SAPPostProcessVO.Material.PItemSpecVersion.builder()
                    .plntCd(currentVersionPItemSpec.getPlntCd())
                    .currentAitmSpecIdx(currentVersionPItemSpec.getAitmSpecIdx())
                    .crtUid(PlantType.SYSTEM.getUid())
                    .crtIp(PlantType.SYSTEM.getIp())
                    .udtUid(PlantType.SYSTEM.getUid())
                    .udtIp(PlantType.SYSTEM.getIp())
                    .build();

            SAPPostProcessVO.Material.PItemSpecVersion pItemSpecVersion = postProcessDao.findPItemSpecVersionNextKey(specVersion);
            specVersion.setAitmSpecIdx(pItemSpecVersion.getAitmSpecIdx());
            specVersion.setAitmSpecVer(pItemSpecVersion.getAitmSpecVer());

            postProcessDao.createPItemSpecVersion(specVersion);
            postProcessDao.createPItemSpecAitm(specVersion);
            alreadyApprovedPackageTestSpecIdxOrNull = specVersion.getAitmSpecIdx();
        }

        SAPPostProcessVO.Material.PItemSpec newSpec = SAPPostProcessVO.Material.PItemSpec.builder()
                .plntCd(pitemKey.getPlntCd())
                .pitmCd(pitemKey.getPitmCd())
                .pitmSpecIdx(nextSpecIdx)
                .currentVersion(currentVersionPItemSpec.getPitmVer())
                .aitmSpecIdx(alreadyApprovedPackageTestSpecIdxOrNull)
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

    private Map<String, SAPMaterialVO.Mara> toMaterialMapByMaterialCode(List<SAPMaterialVO.Mara> latestMara) {
        return latestMara.stream().collect(Collectors.toMap(SAPMaterialVO.Mara::getMatnr, o -> o, (oldValue, newValue) -> newValue));
    }

    private boolean isFinishOrPackagingMaterial(SAPMaterialVO.Mara mara) {
        return mara != null && (SAPPItemType.isFinished(mara.getMatnr()) || SAPPItemType.isPackaging(mara.getMatnr()));
    }

    private boolean isSemiMaterialByELN(SAPMaterialVO.Mara mara, SAPMaterialVO.Marc marc) {
        return mara != null && SAPPItemType.isSemiByELN(mara.getMtart(), marc.getDispo());
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