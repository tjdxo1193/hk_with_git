package lims.api.sy.controller;

import lims.api.sy.service.LoginHistoryService;
import lims.api.sy.vo.LoginHistoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sy/loginHistory")
public class LoginHistoryController {
    private final LoginHistoryService service;

    @GetMapping
    public ResponseEntity<List<LoginHistoryVO>> get(LoginHistoryVO request) {

        return ResponseEntity.ok(service.find(request));
    }
}
