package lims.api.st.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.st.service.StabPlanDetailApprService;
import lims.api.st.vo.StabPlanDetailApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/st/stabPlanDetailAppr")
public class StabPlanDetailApprController {

    private final StabPlanDetailApprService stabPlanDetailApprService;
    private final JwtResolver jwtResolver;

    // 안정성상세계획 승인 조회
    @GetMapping
    public ResponseEntity<List<StabPlanDetailApprVO>> findAll(@AuthToken Token token, StabPlanDetailApprVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(stabPlanDetailApprService.findAll(param));
    }

    // 안정성시험계획 승인
    @PutMapping("/approve")
    public ResponseEntity<Integer> approve(@AuthToken Token token, @RequestBody List<StabPlanDetailApprVO> param) {
        param.forEach(item -> this.setLoginUserData(token, item));
//        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(stabPlanDetailApprService.approve(param));
    }

    // 안정성시험계획 반려
    @PutMapping("/reject")
    public ResponseEntity<Integer> reject(@AuthToken Token token, @RequestBody List<StabPlanDetailApprVO> param) {
        param.forEach(item -> this.setLoginUserData(token, item));
//        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(stabPlanDetailApprService.reject(param));
    }

    public StabPlanDetailApprVO setLoginUserData(Token token, StabPlanDetailApprVO param) {
            String jwt = token.getJwt();
            String plntCd = jwtResolver.getPlantCode(jwt);
            String loginUserUId = jwtResolver.getUserId(jwt);

            param.setPlntCd(plntCd);
            param.setLoginUserUid(loginUserUId);

            return param;
        }
}