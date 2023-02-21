package lims.api.ms.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.ms.enums.SpecProgress;
import lims.api.ms.service.ItemApprService;
import lims.api.ms.vo.ItemManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}//ms/itemAppr")
public class ItemApprController {
    private final ItemApprService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<ItemManageVO>> get(@AuthToken Token token, ItemManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setAprReqUid(jwtResolver.getUserId(jwt));
        request.setAprIp(HttpHelper.getClientIp());
        return ResponseEntity.ok(service.find(request));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> approveItem(@AuthToken Token token, @RequestBody List<ItemManageVO> requests) {
        String jwt = token.getJwt();
        String ip = HttpHelper.getClientIp();
        String uid = jwtResolver.getUserId(jwt);
        for (ItemManageVO request : requests) {
            request.setAprIp(ip);
            request.setAprUid(uid);
        }
        service.approve(requests);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/reject")
    public ResponseEntity<CommonResponse> rejectItem(@AuthToken Token token, @RequestBody List<ItemManageVO> requests) {
        String jwt = token.getJwt();
        String uid = jwtResolver.getUserId(jwt);
        for (ItemManageVO request : requests) {
            request.setRjtUid(uid);
        }
        service.reject(requests);
        return ResponseEntity.ok(new CommonResponse());
    }
}