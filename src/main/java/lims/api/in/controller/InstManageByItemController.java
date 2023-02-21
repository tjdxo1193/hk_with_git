package lims.api.in.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.in.service.InstManageByItemService;
import lims.api.in.vo.InstManageByItemVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/in/instManageByItem")
public class InstManageByItemController {
    private final InstManageByItemService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<InstManageByItemVO>> getItem(@AuthToken Token token, InstManageByItemVO request) {
        request.setPlntCd(getPlantCode(token));
        return ResponseEntity.ok(service.findItem(request));
    }

    @GetMapping("/detail")
    public ResponseEntity<List<InstManageByItemVO>> getDetail(@AuthToken Token token, InstManageByItemVO request) {
        request.setPlntCd(getPlantCode(token));
        return ResponseEntity.ok(service.findDetail(request));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@AuthToken Token token, @RequestBody InstManageByItemVO request) {
        request.setPlntCd(getPlantCode(token));
        return null;
    }

    private String getPlantCode(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getPlantCode(jwt);
    }
}