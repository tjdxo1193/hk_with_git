package lims.api.ms.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.ms.service.TestCycleService;
import lims.api.ms.vo.TestCycleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ms/testCycle")
public class TestCycleController {
    private final TestCycleService testCycleService;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<TestCycleVO>> getList(@AuthToken Token token, TestCycleVO param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);
        return ResponseEntity.ok(testCycleService.getList(param));
    }

    @PostMapping
    public ResponseEntity<CommonResponse> create(@AuthToken Token token, @RequestBody TestCycleVO param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);

        testCycleService.create(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@AuthToken Token token, @RequestBody TestCycleVO param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);

        testCycleService.update(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/delete")
    public ResponseEntity<CommonResponse> delete(@AuthToken Token token, @RequestBody TestCycleVO param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);

        testCycleService.delete(param);
        return ResponseEntity.ok(new CommonResponse());
    }
}