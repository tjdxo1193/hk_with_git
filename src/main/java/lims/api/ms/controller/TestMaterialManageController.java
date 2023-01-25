package lims.api.ms.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.ms.service.TestMaterialManageService;
import lims.api.ms.vo.TestMaterialVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ms/testMaterialManage")
public class TestMaterialManageController {

    private final TestMaterialManageService manageService;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<TestMaterialVO>> masters(TestMaterialVO param) {
        return ResponseEntity.ok(manageService.getMasters(param));
    }

    @PostMapping
    public ResponseEntity<TestMaterialVO> create(@AuthToken Token token, @RequestBody TestMaterialVO param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);

        return ResponseEntity.ok(manageService.create(param));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@RequestBody TestMaterialVO param) {
        manageService.update(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/delete")
    public ResponseEntity<CommonResponse> delete(@RequestBody TestMaterialVO param) {
        manageService.delete(param);
        return ResponseEntity.ok(new CommonResponse());
    }

}