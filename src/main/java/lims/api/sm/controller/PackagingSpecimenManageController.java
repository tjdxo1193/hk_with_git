package lims.api.sm.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.sm.service.PackagingSpecimenManageService;
import lims.api.sm.vo.PackagingSpecimenManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sm/packagingSpecimenManage")
public class PackagingSpecimenManageController {

    private final PackagingSpecimenManageService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<PackagingSpecimenManageVO>> getPackagingSpecimenList(@AuthToken Token token, PackagingSpecimenManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getPackagingSpecimenList(request));
    }

    @GetMapping("/getPackagingSpecimenAcsr")
    public ResponseEntity<List<PackagingSpecimenManageVO>> getPackagingSpecimenAcsr(@AuthToken Token token, Integer pmSpcmIdx) {
        String jwt = token.getJwt();
        PackagingSpecimenManageVO request = new PackagingSpecimenManageVO();
        request.setPmSpcmIdx(pmSpcmIdx);
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getPackagingSpecimenAcsr(request));
    }

    @PutMapping("/save")
    public ResponseEntity<CommonResponse> save(@AuthToken Token token, @RequestBody PackagingSpecimenManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        service.save(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/deleteRequest")
    public ResponseEntity<CommonResponse> deleteRequest(@AuthToken Token token, @RequestBody PackagingSpecimenManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        service.deleteRequest(request);
        return ResponseEntity.ok(new CommonResponse());
    }
}