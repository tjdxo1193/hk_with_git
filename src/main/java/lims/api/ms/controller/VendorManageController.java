package lims.api.ms.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.ms.service.VendorManageService;
import lims.api.ms.vo.VendorVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ms/vendor")
public class VendorManageController {

    private final VendorManageService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<VendorVO>> findAll(VendorVO param) {
        return ResponseEntity.ok(service.findAll(param));
    }

    @PostMapping
    public ResponseEntity<CommonResponse> create(@AuthToken Token token, @RequestBody VendorVO param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);

        service.create(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@RequestBody VendorVO param) {
        service.update(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/delete")
    public ResponseEntity<CommonResponse> delete(@RequestBody VendorVO param) {
        service.delete(param);
        return ResponseEntity.ok(new CommonResponse());
    }

}