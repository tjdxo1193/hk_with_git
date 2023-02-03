package lims.api.integration.service.impl.postProcessor.sap;

import lims.api.common.enums.PlantType;
import lims.api.common.enums.UseType;
import lims.api.integration.annotation.PostProcessorRevExceptionHandler;
import lims.api.integration.dao.SAPDao;
import lims.api.integration.dao.SAPMasterDao;
import lims.api.integration.dao.SAPPostProcessDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.enums.MaterialCharCode;
import lims.api.integration.exception.IntegrationNoSavedException;
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
    private final SAPMaterialSpecStrategy sapMaterialSpecStrategy;

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
                masterDao.findMaterialMara(degree),
                masterDao::createMaterialMara,
                masterDao::updateMaterialMara,
                (mara, existsMara) -> {
                    if (existsMara != null && !StringUtils.equals(mara.getZlabno(), existsMara.getZlabno())) {
                        mara.markingChangeLabNo();
                    }
                    differentVO.getMara().add(mara);
                }
        ).getTotalCount();

        List<SAPMaterialVO.Marc> marcs = sapDao.findMarcAllByDegree(degree);
        count += new SimpleSaveProcess<SAPMaterialVO.Marc>().forEachSave(
                marcs,
                masterDao.findMaterialMarc(degree),
                masterDao::createMaterialMarc,
                masterDao::updateMaterialMarc,
                (marc, existsMara) -> differentVO.getMarc().add(marc)
        ).getTotalCount();

        List<SAPMaterialVO.Mvke> mvkes = sapDao.findMvkeAllByDegree(degree);
        count += new SimpleSaveProcess<SAPMaterialVO.Mvke>().forEachSave(
                mvkes,
                masterDao.findMaterialMvke(degree),
                masterDao::createMaterialMvke,
                masterDao::updateMaterialMvke,
                (mvke, existsMvke) -> differentVO.getMvke().add(mvke)
        ).getTotalCount();

        List<SAPMaterialVO.Zmdv> zmdvs = sapDao.findZmdvAllByDegree(degree);
        count += new SimpleSaveProcess<SAPMaterialVO.Zmdv>().forEachSave(
                zmdvs,
                masterDao.findMaterialZmdv(degree),
                masterDao::createMaterialZmdv,
                masterDao::updateMaterialZmdv,
                (zmdv, existsZmdv) -> differentVO.getZmdv().add(zmdv)
        ).getTotalCount();

        List<SAPMaterialVO.Makt> makts = sapDao.findMaktAllByDegree(degree);
        count += new SimpleSaveProcess<SAPMaterialVO.Makt>().forEachSave(
                makts,
                masterDao.findMaterialMakt(degree),
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
        SAPMaterialVO.Mara diffMaterial;
        SAPMaterialVO.Mara material;
        SAPMaterialVO.Marc matchedMarc;

//        List<SAPPostProcessVO.Material.PItemInfoSap> infoSapsByLabNo;
//        SAPPostProcessVO.Material.PItemKey pItemKeyWithLabNo;
//        SAPPostProcessVO.Material.PItemKey pitemKeyByLabNo;

        /**
         * @implNote
         *  품목 최초생성                             -> 새 품목 버전 생성 및 새 규격서 생성
         *  품목 임시저장                             -> 기존 버전 비활성화 및 새 품목 버전 생성 (상태값 = 승인완료)
         *  품목 승인완료 &
         *      규격서 [임시저장, 검토반려]             -> 기존 품목의 SAP 연계 필드들을 새로 연계받은 데이터로 업데이트
         *      규격서 [검토요청, 승인요청, 승인반려]    -> 기존 버전 비활성화 및 새 품목 버전 생성 (상태값 = 승인완료)
         *                                             기존 규격서 비활성화 및 새 규격서 생성 (상태값 = 규격서 삭제)
         *      규격서 [승인완료]                     -> 기존 버전 비활성화 및 새 품목 버전 생성 (상태값 = 승인완료)
         *                                             기존 규격서 비활성화 및 새 규격서 생성 (상태값 = 승인완료)
         */
        for (SAPPostProcessVO.Material.PItemKey pitemKey : pitemKeys) {
            material = materialMap.get(pitemKey.getPitmCd());

            diffMaterial = getDiffMaterial(latestMara, pitemKey);
            material.setChangedLabNo(diffMaterial.isChangedLabNo());

            matchedMarc = getMarcByPItemKey(latestMarc, pitemKey);

            boolean noExistsPItem = !postProcessDao.existsPItem(pitemKey);
            if (noExistsPItem) {
                int pitemNewVersion = postProcessDao.nextVersionOfPItem(pitemKey);
                createFirstVersionOfPItem(pitemNewVersion, matchedMarc, pitemKey);
                createFirstVersionOfPItemSpec(pitemNewVersion, material, pitemKey);
                continue;
            }

            pItemKeyWithCurrentPItemVersion = SAPPostProcessVO.Material.PItemKey.builder()
                    .plntCd(pitemKey.getPlntCd())
                    .pitmCd(pitemKey.getPitmCd())
                    .version(postProcessDao.currentVersionOfPItem(pitemKey))
                    .build();
            String pitmStatusCode = postProcessDao.currentFinalVersionStatusCodeOfPItem(pItemKeyWithCurrentPItemVersion);
            String specStatusCode = postProcessDao.currentFinalVersionStatusCodeOfSpec(pItemKeyWithCurrentPItemVersion);

            assertExistsCode(pitmStatusCode, pitemKey);
            assertExistsCode(specStatusCode, pitemKey);

            if (ItemManage.TEMP_SAVE.equals(pitmStatusCode)) {
                updateInterfaceDataOfCurrentPItem(matchedMarc, pItemKeyWithCurrentPItemVersion);
                continue;
            }

            if (ItemManage.APRROVED.equals(pitmStatusCode)) {
                if (SpecProgress.TEMPORARY_STORAGE.equals(specStatusCode) || SpecProgress.REVIEW_RETURN.equals(specStatusCode)) {
                    updateInterfaceDataOfCurrentPItem(matchedMarc, pItemKeyWithCurrentPItemVersion);

//                    if (sapMaterialSpecStrategy.shouldBeSpecVersionIdxIsNull(material)) {
//                        sapMaterialSpecStrategy.updateToNullPItemSpecVersion(pItemKeyWithCurrentPItemVersion);
//                    }
                    continue;
                }

                Integer nextPItemVersion = postProcessDao.nextVersionOfPItem(pitemKey);

                updateInactiveOfCurrentPItem(pItemKeyWithCurrentPItemVersion, SpecProgress.APPROVED);
                versionUpOfPItem(nextPItemVersion, matchedMarc, pItemKeyWithCurrentPItemVersion);

                createOrVersionUpPItemSpec(pItemKeyWithCurrentPItemVersion, nextPItemVersion, material, matchedMarc, specStatusCode);

//                if (sapMaterialSpecStrategy.shouldBeSpecVersionIdxIsNull(material)) {
//                    String labNo = Optional.ofNullable(material.getZlabno()).orElseThrow(() -> new NoSuchElementException("No exists changed Lab No in SAP Mara."));
//                    pItemKeyWithLabNo = SAPPostProcessVO.Material.PItemKey.builder()
//                            .plntCd(pItemKeyWithCurrentPItemVersion.getPlntCd())
//                            .pitmCd(pItemKeyWithCurrentPItemVersion.getPitmCd())
//                            .version(pItemKeyWithCurrentPItemVersion.getVersion())
//                            .labNo(labNo)
//                            .build();
//
//                    infoSapsByLabNo = postProcessDao.findAllByLabNo(pItemKeyWithLabNo);
//                    for (SAPPostProcessVO.Material.PItemInfoSap infoSap : infoSapsByLabNo) {
//                        pitemKeyByLabNo = SAPPostProcessVO.Material.PItemKey.builder()
//                                .plntCd(infoSap.getPlntCd())
//                                .pitmCd(infoSap.getPitmCd())
//                                .version(infoSap.getPitmVer())
//                                .build();
//                        createOrVersionUpPItemSpec(
//                                pitemKeyByLabNo,
//                                pitemKeyByLabNo.getVersion(),
//                                sapMaterialSpecStrategy.getMaterial(pitemKeyByLabNo),
//                                sapMaterialSpecStrategy.getMarc(pitemKeyByLabNo),
//                                postProcessDao.currentFinalVersionStatusCodeOfSpec(pitemKeyByLabNo));
//                    }
//                }
            }
        }
    }

    private SAPMaterialVO.Mara getDiffMaterial(List<SAPMaterialVO.Mara> materials, SAPPostProcessVO.Material.PItemKey pitemKey) {
        return materials.stream()
                .filter(mara -> mara.getMatnr().equals(pitemKey.getPitmCd()))
                .findAny()
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Not found be matched material code(= pitm_cd) in different data. Must exist in diff data.");
                });
    }

    private void createOrVersionUpPItemSpec(SAPPostProcessVO.Material.PItemKey pItemKeyWithCurrentPItemVersion, Integer nextPItemVersion,
                                            SAPMaterialVO.Mara material, SAPMaterialVO.Marc marc, String specStatusCode) {
        if (SpecProgress.APPROVAL_REQUEST.equals(specStatusCode) || SpecProgress.REQUEST_REVIEW.equals(specStatusCode) ||
                SpecProgress.APPROVAL_REJECTION.equals(specStatusCode)) {
            sapMaterialSpecStrategy.updateInactiveOfCurrentPItemSpec(
                    pItemKeyWithCurrentPItemVersion.getPlntCd(),
                    pItemKeyWithCurrentPItemVersion.getPitmCd(),
                    pItemKeyWithCurrentPItemVersion.getVersion(),
                    SpecProgress.SPEC_REMOVE);
            sapMaterialSpecStrategy.versionUpOfPItemSpec(
                    pItemKeyWithCurrentPItemVersion.getPlntCd(),
                    pItemKeyWithCurrentPItemVersion.getPitmCd(),
                    nextPItemVersion,
                    material,
                    marc);
            return;
        }

        if (SpecProgress.APPROVED.equals(specStatusCode)) {
            sapMaterialSpecStrategy.updateInactiveOfCurrentPItemSpec(
                    pItemKeyWithCurrentPItemVersion.getPlntCd(),
                    pItemKeyWithCurrentPItemVersion.getPitmCd(),
                    pItemKeyWithCurrentPItemVersion.getVersion(),
                    SpecProgress.APPROVED);
            sapMaterialSpecStrategy.versionUpOfPItemSpec(
                    pItemKeyWithCurrentPItemVersion.getPlntCd(),
                    pItemKeyWithCurrentPItemVersion.getPitmCd(),
                    nextPItemVersion,
                    material,
                    marc);
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
        SAPPostProcessVO.Material.PItemInfo pitemInfo = SAPPostProcessVO.Material.PItemInfo.ofFirstVersion(pitemKey, newVersion);
        pitemInfo.setPItemTypByMRPCode(marc.getDispo());
        createNextVersionOfPItem(newVersion, pitemKey, SpecProgress.TEMPORARY_STORAGE, pitemInfo);
    }

    private void versionUpOfPItem(Integer newVersion, SAPMaterialVO.Marc marc, SAPPostProcessVO.Material.PItemKey pItemKeyWithCurrentPItemVersion) {
        SAPPostProcessVO.Material.PItemInfo pitemInfo = postProcessDao.findPItmInfoAllByKey(pItemKeyWithCurrentPItemVersion);
        pitemInfo.setPItemTypByMRPCode(marc.getDispo());
        pitemInfo.setPitmVer(newVersion);
        createNextVersionOfPItem(newVersion, pItemKeyWithCurrentPItemVersion, SpecProgress.APPROVED, pitemInfo);
    }

    private void createNextVersionOfPItem(Integer newVersion, SAPPostProcessVO.Material.PItemKey pitemKey, SpecProgress progress,
                                          SAPPostProcessVO.Material.PItemInfo pitemInfo) {
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

        postProcessDao.createPItemInfo(pitemInfo);

        SAPPostProcessVO.Material.PItemInfoSap pitemInfoSap = createPItemInfoSAPByCreateAndUpdate(pitemKey.getPlntCd(), pitemKey.getPitmCd(), newVersion);
        postProcessDao.createPItemInfoSap(pitemInfoSap);
    }

    private void updateInactiveOfCurrentPItem(SAPPostProcessVO.Material.PItemKey pitemKey, SpecProgress specProgress) {
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
    }

    private void updateInterfaceDataOfCurrentPItem(SAPMaterialVO.Marc marc, SAPPostProcessVO.Material.PItemKey pitemKey) {
        SAPPostProcessVO.Material.PItemInfo pitemInfo = SAPPostProcessVO.Material.PItemInfo.of(pitemKey);
        pitemInfo.setPItemTypByMRPCode(marc.getDispo());
        postProcessDao.updatePItemInfo(pitemInfo);

        SAPPostProcessVO.Material.PItemInfoSap pitemInfoSap = createPItemInfoSAPByCreateAndUpdate(pitemKey.getPlntCd(), pitemKey.getPitmCd(), pitemKey.getVersion());
        postProcessDao.updatePItemInfoSap(pitemInfoSap);
    }

    private void createFirstVersionOfPItemSpec(Integer pitmVersion, SAPMaterialVO.Mara material, SAPPostProcessVO.Material.PItemKey pitemKey) {
        Integer alreadyApprovedPackageTestSpecIdxOrNull = null;
        if (sapMaterialSpecStrategy.isFinishOrPackagingMaterial(material)) {
            alreadyApprovedPackageTestSpecIdxOrNull = sapMaterialSpecStrategy.getAlreadyApprovedPackageSpecIdxOrNull(pitemKey.getPlntCd(), material);
        }

        Integer pitmSpecIdx = postProcessDao.nextPItemSpecIdxOfSpec(pitemKey);

        SAPPostProcessVO.Material.PItemSpec newSpec = SAPPostProcessVO.Material.PItemSpec.builder()
                .plntCd(pitemKey.getPlntCd())
                .pitmCd(pitemKey.getPitmCd())
                .pitmSpecIdx(pitmSpecIdx)
                .pitmVer(pitmVersion)
                .useVerYn(UseType.N)
                .specProcCd(SpecProgress.TEMPORARY_STORAGE)
                .aitmSpecIdx(alreadyApprovedPackageTestSpecIdxOrNull)
                .build();
        postProcessDao.createNewPItemSpec(newSpec);
    }

    private Map<String, SAPMaterialVO.Mara> toMaterialMapByMaterialCode(List<SAPMaterialVO.Mara> latestMara) {
        return latestMara.stream().collect(Collectors.toMap(SAPMaterialVO.Mara::getMatnr, o -> o, (oldValue, newValue) -> newValue));
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

    private void assertExistsCode(String code, SAPPostProcessVO.Material.PItemKey pitemKey) {
        if (StringUtils.isEmpty(code)) {
            String message = String.format(
                    "No exists pitm spec status code. plant code: %s, pitm code: %s, version: %s",
                    pitemKey.getPlntCd(),
                    pitemKey.getPitmCd(),
                    pitemKey.getVersion());
            throw new NoSuchElementException(message);
        }
    }

}