package lims.api.lb.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.lb.service.LabEventApprService;
import lims.api.lb.vo.LabEventApprVO;
import lims.api.lb.vo.LabEventReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/lb/labEventAppr")
public class LabEventApprController {

    private final LabEventApprService labEventApprService;

    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<LabEventApprVO>> findAll(@AuthToken Token token, LabEventApprVO dto) {
        dto.setPlntCd(this.getAuthUserPlntCd(token));
        dto.setLoginUserUid(this.getAuthUserId(token));
        return ResponseEntity.ok(labEventApprService.findAll(dto));
    }

    @PostMapping
    public ResponseEntity<Integer> approve(@AuthToken Token token, @RequestBody List<LabEventApprVO> dto) {
        dto.forEach(item -> {
            item.setLoginUserUid(this.getAuthUserId(token));
            item.setLoginUserIp(HttpHelper.getClientIp());
        });
        return ResponseEntity.ok(labEventApprService.approve(dto));
    }

    @PostMapping("/reject")
    public ResponseEntity<Integer> reject(@AuthToken Token token, @RequestBody List<LabEventApprVO> dto) {
        dto.forEach(item -> {
            item.setLoginUserUid(this.getAuthUserId(token));
        });
        return ResponseEntity.ok(labEventApprService.reject(dto));
    }


    // getByToken methods
    private String getAuthUserPlntCd(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getPlantCode(jwt);
    }

    private String getAuthUserId(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getUserId(jwt);
    }
}