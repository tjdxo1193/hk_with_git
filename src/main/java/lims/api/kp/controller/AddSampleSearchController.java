package lims.api.kp.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.kp.service.AddSampleSearchService;
import lims.api.kp.vo.AddSampleSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/kp/addSampleSearch")
public class AddSampleSearchController {
    private final AddSampleSearchService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<AddSampleSearchVO>> get(@AuthToken Token token, AddSampleSearchVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        return ResponseEntity.ok(service.find(request));
    }
}