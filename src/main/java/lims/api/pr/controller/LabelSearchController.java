package lims.api.pr.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.pr.service.LabelSearchService;
import lims.api.pr.vo.LabelSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/pr/labelSearch")
public class LabelSearchController {

    private final LabelSearchService labelSearchService;
    private final JwtResolver jwtResolver;

    // 라벨출력조회 조회
    @GetMapping
    public ResponseEntity<List<LabelSearchVO>> findAll(@AuthToken Token token, LabelSearchVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(labelSearchService.findAll(param));
    }

    public LabelSearchVO setLoginUserData(Token token, LabelSearchVO param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        String loginUserUId = jwtResolver.getUserId(jwt);

        param.setPlntCd(plntCd);
        param.setLoginUserUid(loginUserUId);

        return param;
    }
}
