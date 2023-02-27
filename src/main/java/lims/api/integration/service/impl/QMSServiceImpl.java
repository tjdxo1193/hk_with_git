package lims.api.integration.service.impl;

import lims.api.integration.dao.QMSDao;
import lims.api.integration.domain.eai.InterfaceSystemFactory;
import lims.api.integration.domain.eai.Publisher;
import lims.api.integration.domain.eai.TrsEventHandler;
import lims.api.integration.domain.qms.MESShiptRepository;
import lims.api.integration.domain.qms.SRMShiptRepository;
import lims.api.integration.domain.qms.ShiptHandler;
import lims.api.integration.enums.DMRItemDiv;
import lims.api.integration.enums.InterfaceSystemType;
import lims.api.integration.enums.MaterialMRP;
import lims.api.integration.enums.TrsInterface;
import lims.api.integration.model.InterfaceTrsResponse;
import lims.api.integration.service.QMSService;
import lims.api.integration.service.TrsService;
import lims.api.integration.vo.QMSSendVO;
import lims.api.integration.vo.SAPMaterialVO;
import lims.api.util.httpClient.factory.HttpEntityFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QMSServiceImpl implements QMSService {

    private final Publisher publisher;
    private final TrsService trsService;
    private final QMSDao qmsDao;

    public QMSServiceImpl(InterfaceSystemFactory factory,
                          HttpEntityFactory entityFactory,
                          TrsService trsService,
                          QMSDao qmsDao) {
        this.publisher = new Publisher(factory.createSystem(InterfaceSystemType.QMS), entityFactory);
        this.trsService = trsService;
        this.qmsDao = qmsDao;
    }

    @Override
    public void publishMaterial(SAPMaterialVO param) {
//        List<SAPMaterialVO.Mara> mara = param.getMara();
        List<SAPMaterialVO.Marc> marc = param.getMarc();
//        List<SAPMaterialVO.Mvke> mvke = param.getMvke();
        List<SAPMaterialVO.Zmdv> zmdv = param.getZmdv();
//        List<SAPMaterialVO.Makt> makt = param.getMakt();

        if (CollectionUtils.isEmpty(marc)) {
            log.warn("[QMSServiceImpl.publishMaterial] No eixsts sap material MARC");
            return;
        }
        if (CollectionUtils.isEmpty(zmdv)) {
            log.warn("[QMSServiceImpl.publishMaterial] No eixsts sap material ZMDV");
            return;
        }

        List<QMSSendVO.Material> dmrCosmeticMaterials = filterTo(zmdv, o -> DMRItemDiv.isCosmetic(o.getCharValChar()));
        List<QMSSendVO.Material> dmrQuasiDrugMaterials = filterTo(zmdv, o -> DMRItemDiv.isQuasiDrug(o.getCharValChar()));

        Map<String, List<QMSSendVO.Material>> marcFinishedMap = groupingByMaterial(marc, o -> MaterialMRP.isDMRFinished(o.getDispo()));
        Map<String, List<QMSSendVO.Material>> marcBulkMap = groupingByMaterial(marc, o -> MaterialMRP.isBulk(o.getDispo()));

        List<QMSSendVO.Material> cosmeticFinishedMaterials = intersect(dmrCosmeticMaterials, marcFinishedMap);
        List<QMSSendVO.Material> cosmeticBulkMaterials = intersect(dmrCosmeticMaterials, marcBulkMap);

        List<QMSSendVO.Material> drugFinishedMaterials = intersect(dmrQuasiDrugMaterials, marcFinishedMap);
        List<QMSSendVO.Material> drugBulkMaterials = intersect(dmrQuasiDrugMaterials, marcBulkMap);



//        QMSSendVO.MaterialAll materialAll = QMSSendVO.MaterialAll.builder()
//                .cosmeticFinisheds(cosmeticFinishedMaterials)
//                .cosmeticBulks(cosmeticBulkMaterials)
//                .drugFinisheds(drugFinishedMaterials)
//                .drugBulks(drugBulkMaterials)
//                .build();
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

    private List<QMSSendVO.Material> filterTo(List<SAPMaterialVO.Zmdv> data, Predicate<SAPMaterialVO.Zmdv> itemDivPredicate) {
        // 화장품, 의약외품 구분 추가속성명
        String charCode = "ZMDE_APPL_REGULATION";
        List<QMSSendVO.Material> dmrMaterials = data.stream()
                .filter(o -> charCode.equals(o.getCharCode()))
                .filter(itemDivPredicate)
                // TODO 벌크 및 완제품에 따라 데이터 세팅
                .map(o -> QMSSendVO.Material.builder()
                        .matnr(o.getMatnr())
                        .itemDiv(DMRItemDiv.of(o.getCharValChar()))
                        .build())
                .collect(Collectors.toList());
        log.info("DMR zmdv material code count: {}", dmrMaterials.size());
        return dmrMaterials;
    }

    private List<QMSSendVO.Material> intersect(List<QMSSendVO.Material> DMRMaterial, Map<String, List<QMSSendVO.Material>> marcMap) {
        Set<QMSSendVO.Material> dmrMaterials = new HashSet<>();
        for (QMSSendVO.Material material : DMRMaterial) {
            if (marcMap.containsKey(material.getMatnr())) {
                List<QMSSendVO.Material> temps = marcMap.get(material.getMatnr());

                for (QMSSendVO.Material temp : temps) {
                    dmrMaterials.add(QMSSendVO.Material.builder()
                            .matnr(material.getMatnr())
                            .itemDiv(material.getItemDiv())
                            .werks(temp.getWerks())
                            .dispo(temp.getDispo())
                            .build());
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