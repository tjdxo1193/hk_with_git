package lims.api.sy.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.sy.service.CommonCodeManageService;
import lims.api.sy.vo.CommonCodeVO;
import lims.api.sy.vo.CommonDetailCodeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/sy/commonCodeManage")
@RequiredArgsConstructor
public class CommonCodeManageController {

    private final CommonCodeManageService service;
    private final JwtResolver jwtResolver;

    @GetMapping("upperCode")
    public ResponseEntity<List<CommonCodeVO>> getUpperCode(CommonCodeVO request){
        return ResponseEntity.ok(service.findUpperCode(request));
    }

    @GetMapping("/detailCode")
    public ResponseEntity<List<CommonDetailCodeVO>> getDetailCode(CommonDetailCodeVO request){
        return ResponseEntity.ok(service.findDetailCode(request));
    }

    @PostMapping("/upperCode")
    public ResponseEntity<CommonResponse> upperCodeInsert(@AuthToken Token token, @RequestBody CommonCodeVO request) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);

        service.upperCodeInsert(request, plntCd);
        service.upperCodeUpdate(request, plntCd);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/detailCode")
    public ResponseEntity<CommonResponse> detailCodeInsert(@AuthToken Token token, @RequestBody CommonDetailCodeVO request) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);

        service.upperDetailCodeInsert(request, plntCd);
        service.upperDetailCodeUpdate(request, plntCd);
        return ResponseEntity.ok(new CommonResponse());
    }

}