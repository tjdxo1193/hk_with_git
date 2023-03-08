package lims.api.integration.controller;

import lims.api.auth.annotation.Permit;
import lims.api.integration.dao.SAPDao;
import lims.api.integration.service.QMSService;
import lims.api.integration.vo.SAPMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/interface/qms")
@RequiredArgsConstructor
public class QMSController {

    private final QMSService qmsService;

    private final SAPDao sapDao;

    @Permit
    @PostMapping("devTest/shipt/{batchNo}")
    public void devTestShipt(@PathVariable String batchNo) {
        qmsService.publishShiptData(batchNo);
    }


    @Permit
    @PostMapping("devTest/material/{degree}")
    public void devTestMaterial(@PathVariable Integer degree) {
        List<SAPMaterialVO.Marc> marc;
        List<SAPMaterialVO.Zmdv> zmdv;
        List<SAPMaterialVO.Makt> makt;
        SAPMaterialVO param;
        for (int i = 1; i <= 458; i++) {
            marc = sapDao.findMarcAllByDegree(i);
            zmdv = sapDao.findZmdvAllByDegree(i);
            makt = sapDao.findMaktAllByDegree(i);

            param = new SAPMaterialVO();
            param.setMarc(marc);
            param.setZmdv(zmdv);
            param.setMakt(makt);
            qmsService.publishMaterial(param);
        }

    }

}