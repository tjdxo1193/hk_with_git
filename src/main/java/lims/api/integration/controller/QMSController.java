package lims.api.integration.controller;

import lims.api.auth.annotation.Permit;
import lims.api.integration.service.QMSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${apiPrefix}/interface/qms")
@RequiredArgsConstructor
public class QMSController {

    private final QMSService qmsService;

    @Permit
    @PostMapping("devTest/shipt/{batchNo}")
    public void devTestShipt(@PathVariable String batchNo) {
        qmsService.publishShiptData(batchNo);
    }

}