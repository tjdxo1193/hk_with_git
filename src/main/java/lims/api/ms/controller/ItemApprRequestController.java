package lims.api.ms.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.ms.service.ItemApprRequestService;
import lims.api.ms.vo.ItemApprRequestVO;
import lims.api.ms.vo.ItemManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}//ms/itemApprRequest")
public class ItemApprRequestController {
    private final ItemApprRequestService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<ItemApprRequestVO>> get(@AuthToken Token token, ItemApprRequestVO param) {
        String jwt = token.getJwt();
        param.setRevwUid(jwtResolver.getUserId(jwt));
        return ResponseEntity.ok(service.find(param));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> requestApprove(@AuthToken Token token, @RequestBody ItemApprRequestVO request) {
        String jwt = token.getJwt();
        request.setRevwIp(HttpHelper.getClientIp());
        request.setRevwUid(jwtResolver.getUserId(jwt));
        service.requestApprove(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/returnReview")
    public ResponseEntity<CommonResponse> rejectReview(@AuthToken Token token, @RequestBody ItemApprRequestVO request) {
        String jwt = token.getJwt();
        request.setRevwIp(HttpHelper.getClientIp());
        request.setRjtUid(jwtResolver.getUserId(jwt));
        service.returnReview(request);
        return ResponseEntity.ok(new CommonResponse());
    }
}