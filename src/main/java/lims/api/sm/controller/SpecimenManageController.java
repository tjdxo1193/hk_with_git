package lims.api.sm.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.sm.service.SpecimenManageService;
import lims.api.sm.vo.SpecimenManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sm/specimenManage")
public class SpecimenManageController {

    private final SpecimenManageService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<SpecimenManageVO>> getSpecimenList(@AuthToken Token token, SpecimenManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getSpecimenList(request));
    }

    @GetMapping("/getSpecimenHis")
    public ResponseEntity<List<SpecimenManageVO>> getSpecimenHis(@AuthToken Token token, Integer spcmIdx) {
        String jwt = token.getJwt();
        SpecimenManageVO request = new SpecimenManageVO();
        request.setSpcmIdx(spcmIdx);
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getSpecimenHis(request));
    }

    @PutMapping("/save")
    public ResponseEntity<CommonResponse> save(@AuthToken Token token, @RequestBody SpecimenManageVO request, @ESign ESignInfo esign) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setChgRea(esign.getReason());
        String udtHis = request.getUdtHis();
        request.setUdtHis(udtHis.replace("&gt;",">"));
        service.save(request);
        return ResponseEntity.ok(new CommonResponse());
    }
}