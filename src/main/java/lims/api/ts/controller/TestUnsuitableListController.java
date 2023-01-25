package lims.api.ts.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.ts.service.TestUnsuitableListService;
import lims.api.ts.vo.TestUnsuitableListVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}//ts/testUnsuitableList")
public class TestUnsuitableListController {

    private final TestUnsuitableListService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<TestUnsuitableListVO>> findAll(@AuthToken Token token, TestUnsuitableListVO param) {
        param.setPlntCd(getAuthUserPlntCd(token));
        return ResponseEntity.ok(service.findAll(param));
    }

    @GetMapping("/{ansIdx}")
    public ResponseEntity<List<TestUnsuitableListVO>> findResultItem(@PathVariable Integer ansIdx) {
        return ResponseEntity.ok(service.findResultItem(ansIdx));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> save(@RequestBody TestUnsuitableListVO param) {
        service.save(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/publish")
    public ResponseEntity<CommonResponse> publish(@RequestBody TestUnsuitableListVO param) {
        service.publish(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    private String getAuthUserPlntCd(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getPlantCode(jwt);
    }

    private String getAuthUserId(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getUserId(jwt);
    }
}