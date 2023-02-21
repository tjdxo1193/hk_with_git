package lims.api.common.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.annotation.Permit;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.service.ComboService;
import lims.api.common.vo.ComboSearchVO;
import lims.api.common.vo.ComboVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @see <a href="https://www.notion.so/Combo-23536817faba4365a3e230b3b5b73f14">...</a>
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/combo")
public class ComboController {

    private final JwtResolver jwtResolver;
    private final ComboService service;

    @GetMapping("/detailUserCommonCode")
    public ResponseEntity<List<ComboVO>> getDetailUserCommonCode(@AuthToken Token token, ComboVO param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        return ResponseEntity.ok(service.getDetailCommonCode(ComboSearchVO.ofParentCodeByUserCode(plntCd, param.getValue())));
    }

    @GetMapping("/detailSystemCommonCode")
    public ResponseEntity<List<ComboVO>> getDetailSystemCommonCode(ComboVO param) {
        return ResponseEntity.ok(service.getDetailCommonCode(ComboSearchVO.ofParentCodeBySystemCode(param.getValue())));
    }

    @GetMapping("/upperMenu")
    public ResponseEntity<List<ComboVO>> getUpperMenu() {
        return ResponseEntity.ok(service.getUpperMenu());
    }

    @GetMapping("/userAuth")
    public ResponseEntity<List<ComboVO>> getUserAuth(@AuthToken Token token) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        return ResponseEntity.ok(service.getUserAuth(plntCd));
    }

    @GetMapping("/upperTreeCd")
    public ResponseEntity<List<ComboVO>> getUpperTreeCd(ComboVO param) {
        return ResponseEntity.ok(service.getUpperTreeCd(param));
    }

    @GetMapping("/treeCd")
    public ResponseEntity<List<ComboVO>> getTreeComboByParentCode(@AuthToken Token token, ComboVO param) {
        String jwt = token.getJwt();
        String plantCode = jwtResolver.getPlantCode(jwt);
        return ResponseEntity.ok(service.getTreeComboByParentCode(ComboSearchVO.ofParentCodeByUserCode(plantCode, param.getValue())));
    }

    @GetMapping("/user")
    public ResponseEntity<List<ComboVO>> getUser(@AuthToken Token token) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        return ResponseEntity.ok(service.getUser(plntCd));
    }

    @GetMapping("/dpt")
    public ResponseEntity<List<ComboVO>> getDpt(@AuthToken Token token) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        return ResponseEntity.ok(service.getDpt(plntCd));
    }

    @GetMapping("/dptByLevel")
    public ResponseEntity<List<ComboVO>> getDptByLevel(@AuthToken Token token, ComboVO param) {
        String jwt = token.getJwt();
        String plantCode = jwtResolver.getPlantCode(jwt);

        return ResponseEntity.ok(service.getDptByLevel(ComboSearchVO.ofLevel(plantCode, Integer.parseInt(param.getValue()))));
    }
    @GetMapping("/ansCyl")
    public ResponseEntity<List<ComboVO>> getAnsCyl() {
        return ResponseEntity.ok(service.getAnsCyl());
    }

    @GetMapping("/amItmCd")
    public ResponseEntity<List<ComboVO>> getAmItmCd() {
        return ResponseEntity.ok(service.getAmItmCd());
    }

    @GetMapping("/getApproverList")
    public ResponseEntity<List<ComboVO>> getApproverList(@AuthToken Token token) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        return ResponseEntity.ok(service.getApproverList(plntCd));
    }

    @GetMapping("/getReviewerList")
    public ResponseEntity<List<ComboVO>> getReviewerList(@AuthToken Token token) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        return ResponseEntity.ok(service.getReviewerList(plntCd));
    }

    @Permit
    @GetMapping("/plntCd")
    public ResponseEntity<List<ComboVO>> getPlntCd() {
        return ResponseEntity.ok(service.getPlntCd());
    }

    @GetMapping("/year")
    public ResponseEntity<List<ComboVO>> getYearList() {
        return ResponseEntity.ok(service.getYearList());
    }

    @GetMapping("/testMaterialTreeCd")
	public ResponseEntity<List<ComboVO>> testMaterialTreeCd(@AuthToken Token token, ComboVO param) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
		return ResponseEntity.ok(service.getTestMaterialTreeCd(param, plntCd));
	}

    @GetMapping("/testTerm")
    public ResponseEntity<List<ComboVO>> testTerm(@AuthToken Token token) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        return ResponseEntity.ok(service.getTestTerm(plntCd));
    }

    @GetMapping("/equipment")
    public ResponseEntity<List<ComboVO>> equipment(@AuthToken Token token) {
        String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        return ResponseEntity.ok(service.getEquipment(plntCd));
    }
}
