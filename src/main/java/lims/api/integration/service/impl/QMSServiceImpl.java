package lims.api.integration.service.impl;

import lims.api.integration.dao.QMSDao;
import lims.api.integration.dao.SAPDao;
import lims.api.integration.domain.eai.InterfaceSystemFactory;
import lims.api.integration.domain.eai.Publisher;
import lims.api.integration.domain.eai.TrsEventHandler;
import lims.api.integration.domain.qms.MESShiptRepository;
import lims.api.integration.domain.qms.SRMShiptRepository;
import lims.api.integration.domain.qms.ShiptHandler;
import lims.api.integration.enums.*;
import lims.api.integration.model.InterfaceTrsResponse;
import lims.api.integration.service.MaterialAdditionAttribute;
import lims.api.integration.service.QMSService;
import lims.api.integration.service.TrsService;
import lims.api.integration.vo.QMSSendVO;
import lims.api.integration.vo.SAPMaterialVO;
import lims.api.util.httpClient.factory.HttpEntityFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QMSServiceImpl implements QMSService {

    private final Publisher publisher;
    private final TrsService trsService;
    private final QMSDao qmsDao;
    private final SAPDao sapDao;

    public QMSServiceImpl(InterfaceSystemFactory factory,
                          HttpEntityFactory entityFactory,
                          TrsService trsService,
                          QMSDao qmsDao,
                          SAPDao sapDao) {
        this.publisher = new Publisher(factory.createSystem(InterfaceSystemType.QMS), entityFactory);
        this.trsService = trsService;
        this.qmsDao = qmsDao;
        this.sapDao = sapDao;
    }

    @Override
    public void publishMaterial(SAPMaterialVO param) {
        List<SAPMaterialVO.Marc> marc = param.getMarc();
        List<SAPMaterialVO.Zmdv> zmdv = param.getZmdv();
        List<SAPMaterialVO.Makt> makt = param.getMakt();

        if (CollectionUtils.isEmpty(marc)) {
            log.warn("[QMSServiceImpl.publishMaterial] No eixsts sap material MARC");
            return;
        }
        if (CollectionUtils.isEmpty(zmdv)) {
            log.warn("[QMSServiceImpl.publishMaterial] No eixsts sap material ZMDV");
            return;
        }
        if (CollectionUtils.isEmpty(makt)) {
            log.warn("[QMSServiceImpl.publishMaterial] No eixsts sap material MAKT");
            return;
        }

        Map<String, List<QMSSendVO.Material>> marcFinishedMap = groupingByMaterial(marc, o -> MaterialMRP.isDMRFinished(o.getDispo()));
        Map<String, List<QMSSendVO.Material>> marcBulkMap = groupingByMaterial(marc, o -> MaterialMRP.isBulk(o.getDispo()));

        MaterialAdditionAttribute attribute = new MaterialAdditionAttribute(zmdv, makt, sapDao.findBusinessPartner());

        List<QMSSendVO.Material> dmrCosmeticMaterials = filter(zmdv, attribute, o -> DMRItemDiv.isCosmetic(o.getCharValChar()));
        List<QMSSendVO.Material> dmrQuasiDrugMaterials = filter(zmdv, attribute, o -> DMRItemDiv.isQuasiDrug(o.getCharValChar()));

        List<QMSSendVO.CosmeticMaterial> cosmeticMaterials = intersect(dmrCosmeticMaterials, marcFinishedMap, QMSSendVO.Material::getCosmeticFinished);
        List<QMSSendVO.CosmeticBulkMaterial> cosmeticBulkMaterials = intersect(dmrCosmeticMaterials, marcBulkMap, QMSSendVO.Material::getCosmeticBulk);
        List<QMSSendVO.DrugMaterial> drugMaterials = intersect(dmrQuasiDrugMaterials, marcFinishedMap, QMSSendVO.Material::getQuasiDrugFinished);
        List<QMSSendVO.DrugBulkMaterial> drugBulkMaterials = intersect(dmrQuasiDrugMaterials, marcBulkMap, QMSSendVO.Material::getQuasiDrugBulk);

        QMSSendVO.MaterialAll materialAll = QMSSendVO.MaterialAll.builder()
                .cosmetics(cosmeticMaterials)
                .cosmeticBulks(cosmeticBulkMaterials)
                .drugs(drugMaterials)
                .drugBulks(drugBulkMaterials)
                .build();

        if (isEmptyData(materialAll)) {
            log.warn("[publishMaterial] No exists data.");
            return;
        }

        trsService.execute(
                TrsInterface.QMS_MATERIAL,
                materialAll,
                qmsDao::nextDegreeInMaterial,
                qmsDao::nextIdxInMaterial,
                new TrsEventHandler<>() {
                    @Override
                    public void saveBeforeSend(QMSSendVO.MaterialAll vo) {
                        int materialIdx = vo.getIdx();
                        qmsDao.createMaterial(vo);

                        for (QMSSendVO.CosmeticMaterial cosmetic : vo.getCosmetics()) {
                            cosmetic.setMaterialIdx(materialIdx);
                            cosmetic.setIdx(qmsDao.nextIdxInCosmetic(materialIdx));
                            qmsDao.createCosmetic(cosmetic);
                        }

                        for (QMSSendVO.CosmeticBulkMaterial cosmeticBulk : vo.getCosmeticBulks()) {
                            cosmeticBulk.setMaterialIdx(materialIdx);
                            cosmeticBulk.setIdx(qmsDao.nextIdxInCosmeticBulk(materialIdx));
                            qmsDao.createCosmeticBulk(cosmeticBulk);
                        }

                        for (QMSSendVO.DrugMaterial drug : vo.getDrugs()) {
                            drug.setMaterialIdx(materialIdx);
                            drug.setIdx(qmsDao.nextIdxInDrug(materialIdx));
                            qmsDao.createDrug(drug);
                        }

                        for (QMSSendVO.DrugBulkMaterial drugBulk : vo.getDrugBulks()) {
                            drugBulk.setMaterialIdx(materialIdx);
                            drugBulk.setIdx(qmsDao.nextIdxInDrugBulk(materialIdx));
                            qmsDao.createDrugBulk(drugBulk);
                        }
                    }

                    @Override
                    public InterfaceTrsResponse send() {
                        return publisher.post(TrsInterface.QMS_MATERIAL.getServicePath(), InterfaceTrsResponse.class, materialAll);
                    }

                    @Override
                    public void saveAfterSend(QMSSendVO.MaterialAll vo, InterfaceTrsResponse response) {
                        qmsDao.updateMaterial(vo);
                    }

                    @Override
                    public void saveOnError(QMSSendVO.MaterialAll vo) {
                        qmsDao.updateMaterial(vo);
                    }
                }

        );
    }

    private List<QMSSendVO.Material> filter(List<SAPMaterialVO.Zmdv> data, MaterialAdditionAttribute attribute, Predicate<SAPMaterialVO.Zmdv> itemDivPredicate) {
        // 화장품, 의약외품을 구분할 수 있는 추가속성명
        String charCode = MaterialCharCode.PITM_DIV.getValue();
        List<QMSSendVO.Material> dmrMaterials = data.stream()
                .filter(o -> charCode.equals(o.getCharCode()) && itemDivPredicate.test(o))
                .map(o -> toMaterialVO(o, attribute))
                .collect(Collectors.toList());
        log.info("DMR zmdv material code count: {}", dmrMaterials.size());
        return dmrMaterials;
    }

    private QMSSendVO.Material toMaterialVO(SAPMaterialVO.Zmdv o, MaterialAdditionAttribute attribute) {
        return QMSSendVO.Material.builder()
                .matnr(o.getMatnr())
                .itemDiv(DMRItemDiv.of(o.getCharValChar()))
                .cosmeticFinished(toCosmeticFinished(o, attribute))
                .cosmeticBulk(toCosmeticBulk(o, attribute))
                .quasiDrugFinished(toDrugFinished(o, attribute))
                .quasiDrugBulk(toDrugBulk(o, attribute))
                .build();
    }

    private QMSSendVO.CosmeticMaterial toCosmeticFinished(SAPMaterialVO.Zmdv o, MaterialAdditionAttribute attribute) {
        String businessPartnerCode = attribute.get(o.getMatnr(), MaterialCharCode.CUSTOMER_CODE);
        return QMSSendVO.CosmeticMaterial.builder()
                .pitmCd(o.getMatnr())
                .pitmNm(attribute.getName(o.getMatnr()))
                .perPitmNm(attribute.getName(o.getMatnr()))
                .pkgUnit(makePackageUnit(o.getMatnr(), attribute))
                .mstFmlNo(makeDmrNo(o.getMatnr()))
                .vdrBpCd(businessPartnerCode)
                .vdrNm(attribute.getBusinessPartnerName(businessPartnerCode))
                .build();
    }

    private QMSSendVO.CosmeticBulkMaterial toCosmeticBulk(SAPMaterialVO.Zmdv o, MaterialAdditionAttribute attribute) {
        String businessPartnerCode = attribute.get(o.getMatnr(), MaterialCharCode.CUSTOMER_CODE);
        return QMSSendVO.CosmeticBulkMaterial.builder()
                .pitmCd(o.getMatnr())
                .pitmNm(attribute.getName(o.getMatnr()))
                .perPitmNm(attribute.getName(o.getMatnr()))
                .vdrBpCd(businessPartnerCode)
                .vdrNm(attribute.getBusinessPartnerName(businessPartnerCode))
                .pitmDiv(attribute.get(o.getMatnr(), MaterialCharCode.PITM_DIV))
                .typDiv(attribute.get(o.getMatnr(), MaterialCharCode.TYPE_DIV))
                .typDtl(attribute.get(o.getMatnr(), MaterialCharCode.TYPE_DIV_DTL))
                .chatDiv(attribute.get(o.getMatnr(), MaterialCharCode.FTN_YN))
                .ftnDiv(attribute.get(o.getMatnr(), MaterialCharCode.FUNCTION_DIV))
                .useLmt(attribute.get(o.getMatnr(), MaterialCharCode.USE_TRM))
                .openLmt(attribute.get(o.getMatnr(), MaterialCharCode.OPEN_LMT))
                .labNo(attribute.get(o.getMatnr(), MaterialCharCode.LAB_NO))
                .fmlNo(makeDmrNo(o.getMatnr()))
                .pkgUnit(makePackageUnit(o.getMatnr(), attribute))
                .build();
    }

    private QMSSendVO.DrugMaterial toDrugFinished(SAPMaterialVO.Zmdv o, MaterialAdditionAttribute attribute) {
        String businessPartnerCode = attribute.get(o.getMatnr(), MaterialCharCode.CUSTOMER_CODE);
        return QMSSendVO.DrugMaterial.builder()
                .pitmCd(o.getMatnr())
                .pitmNm(attribute.getName(o.getMatnr()))
                .pkgUnit(makePackageUnit(o.getMatnr(), attribute))
                .perPitmNm(attribute.getName(o.getMatnr()))
                .vdrBpCd(businessPartnerCode)
                .vdrNm(attribute.getBusinessPartnerName(businessPartnerCode))
                .build();
    }

    private QMSSendVO.DrugBulkMaterial toDrugBulk(SAPMaterialVO.Zmdv o, MaterialAdditionAttribute attribute) {
        return QMSSendVO.DrugBulkMaterial.builder()
                .pitmCd(o.getMatnr())
                .pitmNm(attribute.getName(o.getMatnr()))
                .wrtDt("2023-03-01")
                .wrtId("1")
                .labNo("testlabno")
                .mstFmlNo(makeDmrNo(o.getMatnr()))
                .pitmDiv("PitmDiv")
                .enfoDt("2023-02-28")
                .perNo("12345")
                .fmln("제형및성상")
                .divNo("divNo")
                .pkgUnit(makePackageUnit(o.getMatnr(), attribute))
                .useTrm("12")
                .slvAmtd("시험방법")
                .build();
    }

    private String makeDmrNo(String materialCode) {
        return "B" + materialCode;
    }

    private String makePackageUnit(String materialCode, MaterialAdditionAttribute attribute) {
        return attribute.get(materialCode, MaterialCharCode.MKR_VOL) + attribute.get(materialCode, MaterialCharCode.MKR_VOL_UNIT);
    }

    private Map<String, List<QMSSendVO.Material>> groupingByMaterial(List<SAPMaterialVO.Marc> data, Predicate<SAPMaterialVO.Marc> mrpPredicate) {
        return data.stream()
                .filter(mrpPredicate)
                .collect(Collectors.toMap(
                        SAPMaterialVO.Marc::getMatnr,
                        o -> {
                            List<QMSSendVO.Material> list = new ArrayList<>();
                            list.add(QMSSendVO.Material.builder()
                                    .matnr(o.getMatnr())
                                    .werks(o.getWerks())
                                    .dispo(o.getDispo())
                                    .build());
                            return list;
                        },
                        (oldO, newO) -> {
                            oldO.addAll(newO);
                            return oldO;
                        }));
    }

    private <T> List<T> intersect(List<QMSSendVO.Material> DMRMaterial, Map<String, List<QMSSendVO.Material>> marcMap, Function<QMSSendVO.Material, T> converter) {
        Set<T> dmrMaterials = new HashSet<>();
        QMSSendVO.Material temp;
        for (QMSSendVO.Material material : DMRMaterial) {
            if (marcMap.containsKey(material.getMatnr())) {
                List<QMSSendVO.Material> marcs = marcMap.get(material.getMatnr());

                for (QMSSendVO.Material marc : marcs) {
                    temp = QMSSendVO.Material.builder()
                            .werks(marc.getWerks())
                            .dispo(marc.getDispo())
                            .matnr(material.getMatnr())
                            .itemDiv(material.getItemDiv())
                            .cosmeticFinished(material.getCosmeticFinished())
                            .cosmeticBulk(material.getCosmeticBulk())
                            .quasiDrugFinished(material.getQuasiDrugFinished())
                            .quasiDrugBulk(material.getQuasiDrugBulk())
                            .build();
                    dmrMaterials.add(converter.apply(temp));
                }
            }
        }
        log.info("[QMSServiceImpl.publishMaterial] DMR determine material data count: {}", dmrMaterials.size());
        return new ArrayList<>(dmrMaterials);
    }

    @Override
    public void publishShiptData(String batchNo) {
        ShiptHandler mesHandler = new ShiptHandler(new MESShiptRepository(qmsDao, batchNo));
        ShiptHandler srmHandler = new ShiptHandler(new SRMShiptRepository(qmsDao, batchNo));
        publishShiptDataByHandler(mesHandler);
        publishShiptDataByHandler(srmHandler);
    }

    private void publishShiptDataByHandler(ShiptHandler handler) {
        handler.fetch();
        handler.runValidation();
        if (handler.isReady()) {
            publishShiptData(handler.getData());
        }
    }

    private boolean isEmptyData(QMSSendVO.MaterialAll materialAll) {
        return CollectionUtils.isEmpty(materialAll.getCosmetics()) &&
                CollectionUtils.isEmpty(materialAll.getCosmeticBulks()) &&
                CollectionUtils.isEmpty(materialAll.getDrugs()) &&
                CollectionUtils.isEmpty(materialAll.getDrugBulks());
    }

    @Override
    public void publishShiptData(List<QMSSendVO.ShiptReq> data) {
        trsService.execute(
                TrsInterface.QMS_SHIPT,
                data,
                qmsDao::nextDegreeInShipt,
                qmsDao::nextIdxInShipt,
                new TrsEventHandler<>() {
                    @Override
                    public void saveBeforeSend(QMSSendVO.ShiptReq vo) {
                        qmsDao.createShipt(vo);

                        for (QMSSendVO.ShiptTest test : vo.getTests()) {
                            test.setShiptIdx(vo.getIdx());
                            qmsDao.createShiptTest(test);

                            for (QMSSendVO.ShiptPerform perform : test.getPerforms()) {
                                perform.setShiptTestIdx(test.getShiptIdx());
                                qmsDao.createShiptPerform(perform);
                            }
                        }
                    }

                    @Override
                    public InterfaceTrsResponse send() {
                        return publisher.post(TrsInterface.QMS_SHIPT.getServicePath(), InterfaceTrsResponse.class, Map.of("dataList", data));
                    }

                    @Override
                    public void saveAfterSend(QMSSendVO.ShiptReq vo, InterfaceTrsResponse response) {
                        qmsDao.updateShipt(vo);
                    }

                    @Override
                    public void saveOnError(QMSSendVO.ShiptReq vo) {
                        qmsDao.updateShipt(vo);
                    }
                }
        );
    }

}