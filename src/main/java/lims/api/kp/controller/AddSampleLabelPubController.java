package lims.api.kp.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.kp.service.AddSampleLabelPubService;
import lims.api.kp.vo.AddSampleLabelPubVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/kp/addSampleLabelPub")
public class AddSampleLabelPubController {
    private final AddSampleLabelPubService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<AddSampleLabelPubVO>> get(@AuthToken Token token, AddSampleLabelPubVO request) {
        request.setPlntCd(jwtResolver.getPlantCode(token.getJwt()));
        return ResponseEntity.ok(service.find(request));
    }
}