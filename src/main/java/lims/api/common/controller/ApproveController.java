package lims.api.common.controller;

import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.service.ApproveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/approve")
public class ApproveController {

    private final JwtResolver jwtResolver;
    private final ApproveService service;

}