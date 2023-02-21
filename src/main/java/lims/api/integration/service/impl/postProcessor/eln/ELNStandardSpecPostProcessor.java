package lims.api.integration.service.impl.postProcessor.eln;

import lims.api.integration.annotation.PostProcessorRevExceptionHandler;
import lims.api.integration.dao.ELNDao;
import lims.api.integration.dao.ELNMasterDao;
import lims.api.integration.dao.ELNPostProcessDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.service.impl.postProcessor.PostProcessor;
import lims.api.integration.service.impl.postProcessor.sap.SAPMaterialSpecStrategy;
import lims.api.integration.vo.ELNPostProcessVO;
import lims.api.integration.vo.ELNStandardSpecVO;
import lims.api.integration.vo.SAPMaterialVO;
import lims.api.integration.vo.SAPPostProcessVO;
import lims.api.ms.enums.ELNProductDiv;
import lims.api.ms.enums.SpecProgress;
import lims.api.util.process.KeyObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ELNStandardSpecPostProcessor implements PostProcessor {

    private final ELNDao elnDao;
    private final ELNMasterDao elnMasterDao;
    private final ELNPostProcessDao postProcessDao;
    private final SAPMaterialSpecStrategy sapMaterialSpecStrategy;

    @Override
    @PostProcessorRevExceptionHandler
    public void execute(RevStateful rev) {
        int latestDegree = rev.getDegree();
        List<ELNStandardSpecVO.DifferentKey> differentLabNos = new ArrayList<>();
        saveStandardSpec(latestDegree, differentLabNos);
        updateDifferentDataToPItemSpec(differentLabNos);
    }

    private void saveStandardSpec(Integer degree, List<ELNStandardSpecVO.DifferentKey> differentLabNos) {
        List<ELNStandardSpecVO> oldSpecDtls = elnMasterDao.findStandardSpecDtlAll();

        List<ELNStandardSpecVO> newSpecDtls = elnDao.findLastVersionStandardSpecAll(degree);
        List<ELNStandardSpecVO> newSpecs = newSpecDtls.stream().map(vo -> {
            ELNStandardSpecVO newVO = new ELNStandardSpecVO();
            newVO.setLabNo(vo.getLabNo());
            newVO.setPrdDiv(vo.getPrdDiv());
            return newVO;
        }).distinct().collect(Collectors.toList());

        if (CollectionUtils.isEmpty(oldSpecDtls)) {
            firstSave(differentLabNos, newSpecs, newSpecDtls);
        } else {
            setDifferentData(differentLabNos, oldSpecDtls, newSpecDtls);
            saveWhenAlreadyExistsData(newSpecs, newSpecDtls);
        }
    }

    private void firstSave(List<ELNStandardSpecVO.DifferentKey> differentLabNos, List<ELNStandardSpecVO> newSpecs, List<ELNStandardSpecVO> newSpecDtls) {
        int count = 0;

        for (ELNStandardSpecVO spec : newSpecs) {
            count += elnMasterDao.createStandardSpec(spec);
        }
        for (ELNStandardSpecVO dtl : newSpecDtls) {
            count += elnMasterDao.createStandardSpecDtl(dtl);

            String labNo = dtl.getLabNo();
            ELNProductDiv prdDiv = dtl.getPrdDiv();

            boolean containsLabNo = differentLabNos.stream().anyMatch(diffKey -> diffKey.contains(labNo));
            if (!containsLabNo) {
                differentLabNos.add(new ELNStandardSpecVO.DifferentKey(labNo, prdDiv));
            }
        }

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }
    }

    private void saveWhenAlreadyExistsData(List<ELNStandardSpecVO> newSpecs, List<ELNStandardSpecVO> newSpecDtls) {
        int count = 0;

        elnMasterDao.deleteStandardSpecDtl();
        elnMasterDao.deleteStandardSpec();

        for (ELNStandardSpecVO spec : newSpecs) {
            count += elnMasterDao.createStandardSpec(spec);
        }
        for (ELNStandardSpecVO dtl : newSpecDtls) {
            count += elnMasterDao.createStandardSpecDtl(dtl);
        }

        if (count == 0) {
            throw new IntegrationNoSavedException();
        }
    }

    private void setDifferentData(List<ELNStandardSpecVO.DifferentKey> differentLabNos, List<ELNStandardSpecVO> oldSpecDtls, List<ELNStandardSpecVO> newSpecDtls) {
        Function<ELNStandardSpecVO, KeyObject> keyGenerator = vo -> {
            KeyObject key = new KeyObject();
            key.put("labNo", vo.getLabNo());
            key.put("prdDiv", vo.getPrdDiv());
            return key;
        };
        Map<KeyObject, List<ELNStandardSpecVO>> oldMap = oldSpecDtls.stream().collect(Collectors.groupingBy(keyGenerator));
        Map<KeyObject, List<ELNStandardSpecVO>> newMap = newSpecDtls.stream().collect(Collectors.groupingBy(keyGenerator));

        for (Map.Entry<KeyObject, List<ELNStandardSpecVO>> newEntry : newMap.entrySet()) {
            KeyObject newKey = newEntry.getKey();

            String labNo = String.valueOf(newKey.get("labNo"));
            ELNProductDiv prdDiv = (ELNProductDiv) newKey.get("prdDiv");

            boolean containsLabNo = differentLabNos.stream().anyMatch(diffKey -> diffKey.contains(labNo));
            if (containsLabNo) {
                continue;
            }

            List<ELNStandardSpecVO> newValue = newEntry.getValue();

            boolean isExists = oldMap.containsKey(newKey);
            boolean isNew = !isExists;

            if (isNew) {
                differentLabNos.add(new ELNStandardSpecVO.DifferentKey(labNo, prdDiv));
                continue;
            }

            List<ELNStandardSpecVO> oldValue = oldMap.get(newKey);

            boolean isNotEqualsCount = oldValue.size() != newValue.size();
            if (isNotEqualsCount) {
                differentLabNos.add(new ELNStandardSpecVO.DifferentKey(labNo, prdDiv));
                continue;
            }

            boolean isDiffValue = !oldValue.equals(newValue);
            if (isDiffValue) {
                differentLabNos.add(new ELNStandardSpecVO.DifferentKey(labNo, prdDiv));
            }
        }
    }

    private void updateDifferentDataToPItemSpec(List<ELNStandardSpecVO.DifferentKey> differentLabNos) {
        if (CollectionUtils.isEmpty(differentLabNos)) {
            return;
        }

        for (ELNStandardSpecVO.DifferentKey diffKey : differentLabNos) {
            List<ELNPostProcessVO.PItemSpec> specs = postProcessDao.findFinalSpecByLabNoAndPrdDiv(diffKey);

            /**
             * @implNote
             *  품목 임시저장                             -> 무시? 고려 X ? 확인 필요
             *  품목 승인완료 &
             *      규격서 [임시저장, 검토반려]             -> 기존 품목의 SAP 연계 필드들을 새로 연계받은 데이터로 업데이트
             *      규격서 [검토요청, 승인요청, 승인반려]    -> 기존 버전 비활성화 및 새 품목 버전 생성 (상태값 = 승인완료)
             *                                             기존 규격서 비활성화 및 새 규격서 생성 (상태값 = 규격서 삭제)
             *      규격서 [승인완료]                     -> 기존 버전 비활성화 및 새 품목 버전 생성 (상태값 = 승인완료)
             *                                             기존 규격서 비활성화 및 새 규격서 생성 (상태값 = 승인완료)
             */
            for (ELNPostProcessVO.PItemSpec spec : specs) {
                SpecProgress specProgress = SpecProgress.valueOfCode(spec.getSpecProcCd());
                SAPPostProcessVO.Material.PItemKey pitemKey = SAPPostProcessVO.Material.PItemKey.builder()
                        .plntCd(spec.getPlntCd())
                        .pitmCd(spec.getPitmCd())
                        .version(spec.getPitmVer())
                        .build();

                if (SpecProgress.TEMPORARY_STORAGE == specProgress || SpecProgress.REVIEW_RETURN == specProgress) {
                    sapMaterialSpecStrategy.updateToNullPItemSpecVersion(pitemKey);
                }

                if (SpecProgress.APPROVAL_REQUEST == specProgress || SpecProgress.REQUEST_REVIEW == specProgress ||
                        SpecProgress.APPROVAL_REJECTION == specProgress) {
                    sapMaterialSpecStrategy.updateInactiveOfCurrentPItemSpec(
                            pitemKey.getPlntCd(),
                            pitemKey.getPitmCd(),
                            pitemKey.getVersion(),
                            SpecProgress.SPEC_REMOVE);
                    sapMaterialSpecStrategy.versionUpOfPItemSpec(
                            pitemKey.getPlntCd(),
                            pitemKey.getPitmCd(),
                            pitemKey.getVersion(),
                            getMaterial(pitemKey),
                            sapMaterialSpecStrategy.getMarc(pitemKey));
                    continue;
                }

                if (SpecProgress.APPROVED == specProgress) {
                    sapMaterialSpecStrategy.updateInactiveOfCurrentPItemSpec(
                            pitemKey.getPlntCd(),
                            pitemKey.getPitmCd(),
                            pitemKey.getVersion(),
                            SpecProgress.APPROVED);
                    sapMaterialSpecStrategy.versionUpOfPItemSpec(
                            pitemKey.getPlntCd(),
                            pitemKey.getPitmCd(),
                            pitemKey.getVersion(),
                            getMaterial(pitemKey),
                            sapMaterialSpecStrategy.getMarc(pitemKey));
                }
            }
        }
    }

    private SAPMaterialVO.Mara getMaterial(SAPPostProcessVO.Material.PItemKey pitemKey) {
        SAPMaterialVO.Mara material = sapMaterialSpecStrategy.getMaterial(pitemKey);
        material.markingFromELNStandard();
        return material;
    }

}