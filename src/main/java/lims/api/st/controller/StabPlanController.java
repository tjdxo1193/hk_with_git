package lims.api.st.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.st.service.StabPlanService;
import lims.api.st.vo.StabPlanVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/st/stabPlan")
public class StabPlanController {

    private final StabPlanService stabPlanService;
    private final JwtResolver jwtResolver;

    // 안정성시험계획 조회
    @GetMapping
    public ResponseEntity<List<StabPlanVO>> findAll(@AuthToken Token token, StabPlanVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(stabPlanService.findAll(param));
    }

    // 안정성시험계획 품목 조회
    @GetMapping("/item")
    public ResponseEntity<List<StabPlanVO>> findAllItem(@AuthToken Token token, StabPlanVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(stabPlanService.findAllItem(param));
    }

    // 안정성상세계획 조회
    @GetMapping("/detail")
    public ResponseEntity<Map<String, Object>> getDetail(@AuthToken Token token, StabPlanVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(stabPlanService.getDetail(param));
    }

    // 안정성상세계획 시험항목 조회, validate 용도
    @GetMapping("/getSbtAnsForValidate")
    public ResponseEntity<Integer> getSbtAnsForValidate(@AuthToken Token token, StabPlanVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(stabPlanService.getSbtAnsForValidate(param));
    }


    // 안정성시험계획 저장
    @PostMapping
    public ResponseEntity<Integer> save(@AuthToken Token token, @RequestBody StabPlanVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(stabPlanService.save(param));
    }

    // 안정성시험계획 수정
    @PutMapping
    public ResponseEntity<Integer> update(@AuthToken Token token, @RequestBody StabPlanVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(stabPlanService.update(param));
    }

    // 안정성시험계획 삭제
    @PostMapping ("/delete")
    public ResponseEntity<Integer> delete(@AuthToken Token token, @RequestBody StabPlanVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(stabPlanService.delete(param));
    }

    // 안정성시험계획 중단 요청
    @PutMapping("/stopRequest")
    public ResponseEntity<Integer> stopRequest(@AuthToken Token token, @RequestBody StabPlanVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(stabPlanService.stopRequest(param));
    }

    // 안정성시험계획 중단취소 요청
    @PutMapping("/stopCancelRequest")
    public ResponseEntity<Integer> stopCancelRequest(@AuthToken Token token, @RequestBody StabPlanVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(stabPlanService.stopCancelRequest(param));
    }
    
    // 안정성시험계획 승인 요청
    @PutMapping("/approveRequest")
    public ResponseEntity<Integer> approveRequest(@AuthToken Token token, @RequestBody StabPlanVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(stabPlanService.approveRequest(param));
    }

    // 안정성시험 저장
    @PutMapping("/detail")
    public ResponseEntity<Integer> saveAns(@AuthToken Token token, @RequestBody StabPlanVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(stabPlanService.saveAns(param));
    }

    // 안정성시험 삭제
    @PostMapping("/detail")
    public ResponseEntity<Integer> deleteAns(@AuthToken Token token, @RequestBody StabPlanVO param) {
        param = this.setLoginUserData(token, param);
        return ResponseEntity.ok(stabPlanService.deleteAns(param));
    }

    // 안정성상세계획 저장(체크박스)
    @PutMapping("/detail/saveDetailPlanReg")
    public ResponseEntity<Integer> saveDetailPlanReg(@AuthToken Token token, @RequestBody StabPlanVO param) {
        param = this.setLoginUserData(token, param);

        List<StabPlanVO> detailPlanRegList = param.getDetailPlanRegList();
        detailPlanRegList = detailPlanRegList.stream().map(e -> this.setLoginUserData(token, e)).collect(Collectors.toList());
        param.setDetailPlanRegList(detailPlanRegList);

        return ResponseEntity.ok(stabPlanService.saveDetailPlanReg(param));
    }

    public StabPlanVO setLoginUserData(Token token, StabPlanVO param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        String loginUserUId = jwtResolver.getUserId(jwt);

        param.setPlntCd(plntCd);
        param.setLoginUserUid(loginUserUId);

        return param;
    }

}