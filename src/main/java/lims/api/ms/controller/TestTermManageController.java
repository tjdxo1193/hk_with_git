package lims.api.ms.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.ms.service.TestTermManageService;
import lims.api.ms.vo.TestTermInTerValManageVO;
import lims.api.ms.vo.TestTermManageCodeVO;
import lims.api.ms.vo.TestTermManageVO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/ms/testTermManage")
@RequiredArgsConstructor
public class TestTermManageController {

    private final TestTermManageService testTermManageService;
    private final JwtResolver jwtResolver;

    @GetMapping("/term")
    public ResponseEntity<List<TestTermManageVO>> getTermList(@AuthToken Token token, TestTermManageVO param){
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);
        return ResponseEntity.ok(testTermManageService.getTermList(param));
    }

    @GetMapping("/interval")
    public ResponseEntity<List<TestTermInTerValManageVO>> getIntervalList(@AuthToken Token token, TestTermManageVO param){
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);
        return ResponseEntity.ok(testTermManageService.getIntervalList(param));
    }

    @GetMapping("/getTestCycleDivision")
    public ResponseEntity<List<TestTermManageCodeVO>> getTestCycleDivision(@AuthToken Token token, TestTermManageCodeVO param){
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);
        return  ResponseEntity.ok(testTermManageService.getTestCycleDivision(param));
    }

    @PostMapping("/term")
    public ResponseEntity<CommonResponse> createTerm(@AuthToken Token token, @RequestBody TestTermManageVO param){
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);

        testTermManageService.createTerm(param);
        testTermManageService.createInitInterval(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/term")
    public ResponseEntity<CommonResponse> updateTerm(@AuthToken Token token, @RequestBody TestTermManageVO param){
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);

        testTermManageService.updateTerm(param);

        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/term/delete")
    public ResponseEntity<CommonResponse> deleteTerm(@AuthToken Token token, @RequestBody TestTermManageVO param){
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);

        testTermManageService.deleteTerm(param);

        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/interval")
    public ResponseEntity<CommonResponse> createInterval(@AuthToken Token token, @RequestBody List<TestTermInTerValManageVO> param){
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);

        if(!param.isEmpty()) {
            param.forEach(element -> element.setPlntCd(plntCd));
            testTermManageService.createInterval(param);
        }

        return ResponseEntity.ok(new CommonResponse());
    }

    @PostMapping("/interval/delete")
    public ResponseEntity<CommonResponse> deleteInterval(@AuthToken Token token, @RequestBody TestTermInTerValManageVO param){
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);

        testTermManageService.deleteInterval(param);

        return ResponseEntity.ok(new CommonResponse());
    }
}