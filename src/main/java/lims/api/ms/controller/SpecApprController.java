package lims.api.ms.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.ms.service.SpecApprService;
import lims.api.ms.vo.SpecApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ms/specAppr")
public class SpecApprController {
    private final SpecApprService service;
    private final JwtResolver jwtResolver;
    
    @GetMapping("/version")
    public ResponseEntity<List<SpecApprVO>> getVersionList(@AuthToken Token token, SpecApprVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        param.setLoginUserUid(jwtResolver.getUserId(jwt));
        return ResponseEntity.ok(service.getVersionList(param));
    }

    @GetMapping("/aItem")
    public ResponseEntity<List<SpecApprVO>> getAItemList(@AuthToken Token token, SpecApprVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getAItemList(param));
    }

    @PutMapping("/approval")
    public ResponseEntity<CommonResponse> updateApproval(@AuthToken Token token, @RequestBody List<SpecApprVO> param) {
        String jwt = token.getJwt();

        if(!param.isEmpty()) {
            param.forEach(element ->
            {
                element.setPlntCd(jwtResolver.getPlantCode(jwt));
                element.setAprUid(jwtResolver.getUserId(jwt));
                element.setAprIp(HttpHelper.getClientIp());
            });
            service.updateApproval(param);
        }
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/reject")
    public ResponseEntity<CommonResponse> updateReject(@AuthToken Token token, @RequestBody List<SpecApprVO> param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        String userId = jwtResolver.getUserId(jwt);
        if(!param.isEmpty()) {
            param.forEach(element -> {
                element.setPlntCd(plntCd);
                element.setRjtUid(userId);
            });
            service.updateReject(param);
        }

        return ResponseEntity.ok(new CommonResponse());
    }

}