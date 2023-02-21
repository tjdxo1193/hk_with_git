package lims.api.pr.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.pr.service.RePrintLabelApprService;
import lims.api.pr.vo.RePrintLabelApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/pr/rePrintLabelAppr")
public class RePrintLabelApprController {

    private final RePrintLabelApprService rePrintLabelApprService;
    private final JwtResolver jwtResolver;

    // 라벨재출력승인 조회
    @GetMapping
    public ResponseEntity<List<RePrintLabelApprVO>> findAll(@AuthToken Token token, RePrintLabelApprVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(rePrintLabelApprService.findAll(param));
    }

    // 라벨재출력 승인
    @PutMapping
    public ResponseEntity<Integer> approve(@AuthToken Token token, @RequestBody RePrintLabelApprVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(rePrintLabelApprService.approve(param));
    }

    // 라벨재출력 승인
    @PutMapping("/reject")
    public ResponseEntity<Integer> reject(@AuthToken Token token, @RequestBody RePrintLabelApprVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(rePrintLabelApprService.reject(param));
    }

    public RePrintLabelApprVO setLoginUserData(Token token, RePrintLabelApprVO param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        String loginUserUId = jwtResolver.getUserId(jwt);

        param.setPlntCd(plntCd);
        param.setLoginUserUid(loginUserUId);

        return param;
    }
}