package lims.api.np.controller;

import lims.api.auth.service.impl.JwtResolver;
import lims.api.np.service.NonconformityReportSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/np/nonconformityReportSearch")
public class NonconformityReportSearchController {

    private final NonconformityReportSearchService service;
    private final JwtResolver jwtResolver;
}
