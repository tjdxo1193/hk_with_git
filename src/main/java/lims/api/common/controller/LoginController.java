package lims.api.common.controller;

import lims.api.common.service.LoginService;
import lims.api.common.vo.LoginAuditRecordVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}//login")
public class LoginController {

    private final LoginService service;

    @GetMapping
    public ResponseEntity<List<LoginAuditRecordVO>> getLoginAuditList(@RequestBody LoginAuditRecordVO request) {
        return ResponseEntity.ok(service.getLoginAuditList(request));
    }
}