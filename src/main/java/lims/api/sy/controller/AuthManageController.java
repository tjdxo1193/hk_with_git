package lims.api.sy.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.sy.service.AuthManageService;
import lims.api.sy.vo.AuthManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sy/authManage")
public class AuthManageController {
    private final AuthManageService service;
    private final JwtResolver jwtResolver;

    // 권한 그룹 조회
    @GetMapping
    public ResponseEntity<List<AuthManageVO>> getAth(@AuthToken Token token, AuthManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findAllAth(request));
    }

    // 권한 그룹 추가 및 수정
    @PostMapping
    public ResponseEntity<CommonResponse> insertAth(@AuthToken Token token, @RequestBody AuthManageVO request) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        if (request.getAddedRowItems().size() > 0) {
            service.insert(request, plntCd);
        }
        if (request.getEditedRowItems().size() > 0) {
            service.update(request, plntCd);
        }
        return ResponseEntity.ok(new CommonResponse());
    }

    // 권한이 없는 전체 메뉴 조회
    @GetMapping("notAthGp")
    public ResponseEntity<List<AuthManageVO>> getMenu(@AuthToken Token token, AuthManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setAthCd(request.getAthCd());
        List<AuthManageVO> result = service.findNotAthGp(request);
        return ResponseEntity.ok(result);
    }

    // 권한을 갖고 있는 메뉴 조회
    @GetMapping("athGp")
    public ResponseEntity<List<AuthManageVO>> getAthMenu(@AuthToken Token token, AuthManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        request.setAthCd(request.getAthCd());
        List<AuthManageVO> result = service.findAthGp(request);
        return ResponseEntity.ok(result);
    }

    // 권한 메뉴 생성 및 삭제
    @PostMapping("athGp")
    public ResponseEntity<CommonResponse> updateAthMenu(@AuthToken Token token, @RequestBody AuthManageVO request) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        if (request.getRemovedRowItems().size() > 0) {
            service.deleteAthGp(request, plntCd);
        }
        if (request.getAddedRowItems().size() > 0) {
            service.insertAthGp(request, plntCd);
        }
        return ResponseEntity.ok(new CommonResponse());
    }
}