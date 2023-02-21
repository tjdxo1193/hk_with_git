package lims.api.pr.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.pr.service.PrintLabelService;
import lims.api.pr.vo.PrintLabelVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/pr/printLabel")
public class PrintLabelController {

    private final PrintLabelService printLabelService;
    private final JwtResolver jwtResolver;

    // 라벨출력 조회
    @GetMapping
    public ResponseEntity<List<PrintLabelVO>> findAll(@AuthToken Token token, PrintLabelVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(printLabelService.findAll(param));
    }

    // 라벨출력 프로세스
    @PutMapping
    public ResponseEntity<Integer> printLabel(@AuthToken Token token, @RequestBody List<PrintLabelVO> param) {
        param.forEach(item -> this.setLoginUserData(token, item));
        return ResponseEntity.ok(printLabelService.printLabel(param));
    }

    public PrintLabelVO setLoginUserData(Token token, PrintLabelVO param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        String loginUserUId = jwtResolver.getUserId(jwt);

        param.setPlntCd(plntCd);
        param.setLoginUserUid(loginUserUId);

        return param;
    }
}