package lims.api.sm.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.sm.service.PackagingSpecimenApproveService;
import lims.api.sm.vo.PackagingSpecimenApproveVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sm/packagingSpecimenApprove")
public class PackagingSpecimenApproveController {

    private final PackagingSpecimenApproveService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<PackagingSpecimenApproveVO>> getPackagingSpecimenList(@AuthToken Token token, PackagingSpecimenApproveVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setPmSpcmDelAprUid(jwtResolver.getUserId(jwt));
        return ResponseEntity.ok(service.getPackagingSpecimenList(request));
    }

    @GetMapping("/getPackagingSpecimenAcsr")
    public ResponseEntity<List<PackagingSpecimenApproveVO>> getPackagingSpecimenAcsr(@AuthToken Token token, Integer pmSpcmIdx) {
        String jwt = token.getJwt();
        PackagingSpecimenApproveVO request = new PackagingSpecimenApproveVO();
        request.setPmSpcmIdx(pmSpcmIdx);
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getPackagingSpecimenAcsr(request));
    }

    @PutMapping("/approve")
    public ResponseEntity<CommonResponse> approve(@AuthToken Token token, @RequestBody PackagingSpecimenApproveVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        service.approve(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/reject")
    public ResponseEntity<CommonResponse> reject(@AuthToken Token token, @RequestBody PackagingSpecimenApproveVO request, @ESign ESignInfo esign) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setRjtUid(jwtResolver.getUserId(jwt));
        request.setRjtRea(esign.getReason());
        service.reject(request);
        return ResponseEntity.ok(new CommonResponse());
    }
}