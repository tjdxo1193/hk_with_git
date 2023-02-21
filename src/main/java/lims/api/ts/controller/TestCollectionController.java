package lims.api.ts.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.ESign;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.ESignInfo;
import lims.api.common.model.CommonResponse;
import lims.api.ts.service.TestCollectionService;
import lims.api.ts.vo.TestCollectionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ts/testCollection")
public class TestCollectionController {

    private final TestCollectionService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<TestCollectionVO>> getTestCollectionList(@AuthToken Token token, TestCollectionVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getTestCollectionList(request));
    }

    @PutMapping("/collect")
    public ResponseEntity<CommonResponse> collect(@AuthToken Token token, @RequestBody TestCollectionVO request, @ESign ESignInfo esign) {
        String jwt = token.getJwt();
        request.setClltUid(jwtResolver.getUserId(jwt));
        request.setClltRmk(esign.getReason());
        service.collect(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/save")
    public ResponseEntity<CommonResponse> save(@AuthToken Token token, @RequestBody TestCollectionVO request, @ESign ESignInfo esign) {
        String jwt = token.getJwt();
        request.setClltUid(jwtResolver.getUserId(jwt));
        request.setClltRmk(esign.getReason());
        service.save(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/saveFile")
    public ResponseEntity<Integer> saveFile(@AuthToken Token token, TestCollectionVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.saveFile(request));
    }

}