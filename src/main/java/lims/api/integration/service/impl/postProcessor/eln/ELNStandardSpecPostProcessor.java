package lims.api.integration.service.impl.postProcessor.eln;

import lims.api.common.enums.DeleteType;
import lims.api.common.enums.PlantType;
import lims.api.common.enums.UseType;
import lims.api.integration.exception.IntegrationNoSavedException;
import lims.api.integration.annotation.PostProcessorRevExceptionHandler;
import lims.api.integration.dao.ELNDao;
import lims.api.integration.dao.ELNMasterDao;
import lims.api.integration.dao.ELNPostProcessDao;
import lims.api.integration.domain.eai.RevStateful;
import lims.api.integration.service.impl.postProcessor.PostProcessor;
import lims.api.integration.vo.ELNPostProcessVO;
import lims.api.integration.vo.ELNStandardSpecVO;
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

    @Override
    @PostProcessorRevExceptionHandler
    public void execute(RevStateful rev) {
        int latestDegree = rev.getDegree();
        List<String> differentLabNos = new ArrayList<>();
        saveStandardSpec(latestDegree, differentLabNos);
        updateDifferentDataToPItemSpec(differentLabNos);
    }

    private void saveStandardSpec(Integer degree, List<String> differentLabNos) {
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

    private void firstSave(List<String> differentLabNos, List<ELNStandardSpecVO> newSpecs, List<ELNStandardSpecVO> newSpecDtls) {
        int count = 0;

        for (ELNStandardSpecVO spec : newSpecs) {
            count += elnMasterDao.createStandardSpec(spec);
        }
        for (ELNStandardSpecVO dtl : newSpecDtls) {
            count += elnMasterDao.createStandardSpecDtl(dtl);

            String labNo = dtl.getLabNo();
            if (!differentLabNos.contains(labNo)) {
                differentLabNos.add(labNo);
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

    private void setDifferentData(List<String> differentLabNos, List<ELNStandardSpecVO> oldSpecDtls, List<ELNStandardSpecVO> newSpecDtls) {
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
            if (differentLabNos.contains(labNo)) {
                continue;
            }

            List<ELNStandardSpecVO> newValue = newEntry.getValue();

            boolean isExists = oldMap.containsKey(newKey);
            boolean isNew = !isExists;

            if (isNew) {
                differentLabNos.add(labNo);
                continue;
            }

            List<ELNStandardSpecVO> oldValue = oldMap.get(newKey);

            boolean isNotEqualsCount = oldValue.size() != newValue.size();
            if (isNotEqualsCount) {
                differentLabNos.add(labNo);
                continue;
            }

            boolean isDiffValue = !oldValue.equals(newValue);
            if (isDiffValue) {
                differentLabNos.add(labNo);
            }
        }
    }

    private void updateDifferentDataToPItemSpec(List<String> differentLabNos) {
        if (CollectionUtils.isEmpty(differentLabNos)) {
            return;
        }

        for (String labNo : differentLabNos) {
            List<ELNPostProcessVO.PItemSpec> specs = postProcessDao.findFinalSpecByLabNo(labNo);

            for (ELNPostProcessVO.PItemSpec spec : specs) {
                SpecProgress specProgress = SpecProgress.valueOfCode(spec.getSpecProcCd());

                if (SpecProgress.TEMPORARY_STORAGE.equals(specProgress)) {
                    ELNPostProcessVO.PItemSpec pItemSpec = ELNPostProcessVO.PItemSpec.builder()
                            .plntCd(spec.getPlntCd())
                            .pitmSpecIdx(spec.getPitmSpecIdx())
                            .updatedUid(PlantType.SYSTEM.getUid())
                            .updatedIp(PlantType.SYSTEM.getIp())
                            .build();
                    postProcessDao.updateAitmSpecIdxToNullOfSpec(pItemSpec);
                    continue;
                }

                if (SpecProgress.APPROVAL_REQUEST.equals(specProgress)) {
                    ELNPostProcessVO.PItemSpec pItemSpecForRemove = ELNPostProcessVO.PItemSpec.builder()
                            .plntCd(spec.getPlntCd())
                            .pitmSpecIdx(spec.getPitmSpecIdx())
                            .specProcCd(SpecProgress.SPEC_REMOVE.getCode())
                            .delYn(DeleteType.Y)
                            .updatedUid(PlantType.SYSTEM.getUid())
                            .updatedIp(PlantType.SYSTEM.getIp())
                            .build();
                    postProcessDao.updateStatusOfSpec(pItemSpecForRemove);
                    createNextVersionOfSpec(spec);
                    continue;
                }

                if (SpecProgress.APPROVED.equals(specProgress)) {
                    createNextVersionOfSpec(spec);
                }
            }
        }
    }

    private void createNextVersionOfSpec(ELNPostProcessVO.PItemSpec spec) {
        ELNPostProcessVO.PItemSpec itemSpec = ELNPostProcessVO.PItemSpec.builder()
                .plntCd(spec.getPlntCd())
                .nextPitmSpecIdx(postProcessDao.nextIdxOfSpec(spec))
                .pitmCd(spec.getPitmCd())
                .pitmVer(spec.getPitmVer())
                .specProcCd(SpecProgress.TEMPORARY_STORAGE.getCode())
                .delYn(DeleteType.N)
                .useVerYn(UseType.N)
                .updatedUid(PlantType.SYSTEM.getUid())
                .updatedIp(PlantType.SYSTEM.getIp())
                .build();
        postProcessDao.createNewVersionOfSpec(itemSpec);
    }

}