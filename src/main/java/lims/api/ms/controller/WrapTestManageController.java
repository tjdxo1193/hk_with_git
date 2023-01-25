package lims.api.ms.controller;

import java.util.List;

import lims.api.auth.service.impl.HttpHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.ms.service.WrapTestManageService;
import lims.api.ms.vo.WrapTestManageVO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ms/wrapTestManage")
public class WrapTestManageController {
	private final WrapTestManageService wrapTestManageService;
	private final JwtResolver jwtResolver;
	
	@GetMapping
	public ResponseEntity<List<WrapTestManageVO>> getList(@AuthToken Token token, WrapTestManageVO param) {
		String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);
        
        return ResponseEntity.ok(wrapTestManageService.getList(param));
    }

	@GetMapping("/getOne")
	public ResponseEntity<WrapTestManageVO> getOne(@AuthToken Token token, WrapTestManageVO param) {
		String jwt = token.getJwt();
		String plntCd = jwtResolver.getPlantCode(jwt);
		param.setPlntCd(plntCd);

		return ResponseEntity.ok(wrapTestManageService.getOne(param));
	}
	
	@GetMapping("/getSap")
	public ResponseEntity<List<WrapTestManageVO>> getSapList(WrapTestManageVO param) {
		return ResponseEntity.ok(wrapTestManageService.getSapList(param));
	}
	
	@GetMapping("/getVersion")
	public ResponseEntity<List<WrapTestManageVO>> getVersion(@AuthToken Token token, WrapTestManageVO param) {
		String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);
        
		return ResponseEntity.ok(wrapTestManageService.getVersion(param));
	}
	
	@GetMapping("/getTestItem")
	public ResponseEntity<List<WrapTestManageVO>> getTestItem(@AuthToken Token token, WrapTestManageVO param) {
		String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);
        
		return ResponseEntity.ok(wrapTestManageService.getTestItem(param));
	}
	
	@GetMapping("/getSpec")
	public ResponseEntity<List<WrapTestManageVO>> getSpec(@AuthToken Token token, WrapTestManageVO param) {
		String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        param.setPlntCd(plntCd);
        
		return ResponseEntity.ok(wrapTestManageService.getSpec(param));
	}

	@PostMapping("/putQmPkga")
	public ResponseEntity<Integer> putQmPkGa(@AuthToken Token token, @RequestBody WrapTestManageVO baseData) {
		List<WrapTestManageVO> testItemList = baseData.getTestItemList();
		List<WrapTestManageVO> deleteTestItemList = baseData.getDeleteTestItemList();
		
		String jwt = token.getJwt();
        String plntCd = jwtResolver.getPlantCode(jwt);
        baseData.setPlntCd(plntCd);
        testItemList.forEach(e -> e.setPlntCd(plntCd));
        deleteTestItemList.forEach(e -> e.setPlntCd(plntCd));
        
		return ResponseEntity.ok(wrapTestManageService.putQmPkGa(baseData, testItemList, deleteTestItemList));
	}
	
	@PutMapping
	public ResponseEntity<CommonResponse> approval(@AuthToken Token token, @RequestBody WrapTestManageVO param) {
		String jwt = token.getJwt();
		param.setPlntCd(jwtResolver.getPlantCode(jwt));
        param.setLoginUserUid(jwtResolver.getUserId(jwt));
		param.setLoginUserIp(HttpHelper.getClientIp());
		wrapTestManageService.approval(param);

		return ResponseEntity.ok(new CommonResponse());
	}
}