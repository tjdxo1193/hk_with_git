package lims.api.sy.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.sy.service.DepartmentManageService;
import lims.api.sy.vo.DepartmentManageVO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sy/departmentManage")
public class DepartmentManageController {
    private final JwtResolver jwtResolver;
    private final DepartmentManageService service;

    @GetMapping
    public ResponseEntity<List<DepartmentManageVO>> findAll(@AuthToken Token token, DepartmentManageVO vo) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        vo.setPlntCd(plntCd);
        return ResponseEntity.ok(service.findAll(vo));
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@AuthToken Token token, @RequestBody DepartmentManageVO vo) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        vo.setPlntCd(plntCd);
        if(Strings.isNotEmpty(vo.getHirDptCd())){
            service.insert(vo);
        }
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@AuthToken Token token, @RequestBody DepartmentManageVO vo) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        vo.setPlntCd(plntCd);
        if(Strings.isNotEmpty(vo.getDptCd())) {
            service.update(vo);
        }
        return ResponseEntity.ok(new CommonResponse());
    }

}