package lims.api.ms.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.ms.service.StaffJobDelegationApprovalService;
import lims.api.ms.vo.StaffJobDelegationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}//ms/staffJobDelegationApproval")
public class StaffJobDelegationApprovalController {
    private final StaffJobDelegationApprovalService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<StaffJobDelegationVO>> get(@AuthToken Token token, StaffJobDelegationVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setAprUid(jwtResolver.getUserId(jwt));
        return ResponseEntity.ok(service.find(request));
    }

    @PutMapping("/approve")
    public ResponseEntity<CommonResponse> updateApprove(@AuthToken Token token, @RequestBody List<StaffJobDelegationVO> requests) {
        String jwt = token.getJwt();
        String ip = HttpHelper.getClientIp();

        for (StaffJobDelegationVO row : requests) {
            row.setPlntCd(jwtResolver.getPlantCode(jwt));
            row.setAprReqIp(jwtResolver.getUserId(jwt));
            row.setAprIp(ip);
        }
        service.updateApprove(requests);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/reject")
    public ResponseEntity<CommonResponse> updateReject(@AuthToken Token token, @RequestBody List<StaffJobDelegationVO> requests) {
        String jwt = token.getJwt();
        String ip = HttpHelper.getClientIp();

        for (StaffJobDelegationVO row : requests) {
            row.setPlntCd(jwtResolver.getPlantCode(jwt));
            row.setAprReqIp(jwtResolver.getUserId(jwt));
            row.setAprIp(ip);
        }
        service.updateReject(requests);
        return ResponseEntity.ok(new CommonResponse());
    }
}