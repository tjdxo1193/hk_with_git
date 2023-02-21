package lims.api.pr.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.pr.service.RePrintLabelRequestService;
import lims.api.pr.vo.RePrintLabelRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/pr/rePrintLabelRequest")
public class RePrintLabelRequestController {

    private final RePrintLabelRequestService rePrintLabelRequestService;
    private final JwtResolver jwtResolver;

    // 라벨재출력요청 조회
    @GetMapping
    public ResponseEntity<List<RePrintLabelRequestVO>> findAll(@AuthToken Token token, RePrintLabelRequestVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(rePrintLabelRequestService.findAll(param));
    }

    // 라벨재출력요청 모달 조회
    @GetMapping("/labelList")
    public ResponseEntity<List<RePrintLabelRequestVO>> findLabelList(@AuthToken Token token, RePrintLabelRequestVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(rePrintLabelRequestService.findLabelList(param));
    }

    // 라벨재출력요청
    @PutMapping
    public ResponseEntity<Integer> rePrintLabelRequest(@AuthToken Token token, @RequestBody RePrintLabelRequestVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(rePrintLabelRequestService.rePrintLabelRequest(param));
    }

    // 라벨재출력취소
    @PutMapping("/cancel")
    public ResponseEntity<Integer> rePrintLabelCancel(@AuthToken Token token, @RequestBody RePrintLabelRequestVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(rePrintLabelRequestService.rePrintLabelCancel(param));
    }

    public RePrintLabelRequestVO setLoginUserData(Token token, RePrintLabelRequestVO param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        String loginUserUId = jwtResolver.getUserId(jwt);

        param.setPlntCd(plntCd);
        param.setLoginUserUid(loginUserUId);

        return param;
    }
}