package lims.api.ms.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.ms.service.StaffJobDelegationService;
import lims.api.ms.vo.StaffJobDelegationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ms/staffJobDelegation")
public class StaffJobDelegationController {
    private final StaffJobDelegationService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<StaffJobDelegationVO>> get(StaffJobDelegationVO request) {
        return ResponseEntity.ok(service.find(request));
    }

    // 업무 위임
    @PostMapping
    public ResponseEntity<CommonResponse> create(@AuthToken Token token, @RequestBody StaffJobDelegationVO request) {
        String jwt = token.getJwt();
        String userId = jwtResolver.getUserId(jwt);
        request.setUserId(userId);
        service.create(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    // 수정
    @PutMapping
    public ResponseEntity<CommonResponse> update(@AuthToken Token token, @RequestBody StaffJobDelegationVO request) {
        String jwt = token.getJwt();
        request.setUserId(jwtResolver.getUserId(jwt));
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        service.update(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    // 승인 요청
    @PutMapping("/apprRequest")
    public ResponseEntity<CommonResponse> updateApprRequest(@AuthToken Token token, @RequestBody StaffJobDelegationVO request) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        request.setPlntCd(plntCd);
        service.approveRequest(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    // 재실 등록
    @PutMapping("/gbkRegist")
    public ResponseEntity<CommonResponse> updateGbkRegist(@AuthToken Token token, @RequestBody StaffJobDelegationVO request) {
        String jwt = token.getJwt();
        String userId = jwtResolver.getUserId(jwt);
        request.setUserId(userId);
        service.updateGbkRegist(request);
        return ResponseEntity.ok(new CommonResponse());
    }

}