package lims.api.in.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.in.service.InstHisManageService;
import lims.api.in.vo.InstHisManageVO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/in/instHisManage")
public class InstHisManageController {

	private final InstHisManageService service;
	private final JwtResolver jwtResolver;

	@GetMapping
	public ResponseEntity<List<InstHisManageVO>> findAll(@AuthToken Token token, InstHisManageVO param) {
		param.setPlntCd(getAuthUserPlntCd(token));
		return ResponseEntity.ok(service.findAll(param));
	}

	@PostMapping
	public ResponseEntity<Integer> save(@AuthToken Token token, InstHisManageVO param) {
		param.setPlntCd(getAuthUserPlntCd(token));
		param.setRgtUid(getAuthUserId(token));
		return ResponseEntity.ok(service.save(param));
	}

	@PostMapping("/asList")
	public ResponseEntity<CommonResponse> saveAsList(@AuthToken Token token, @RequestBody List<InstHisManageVO> list) {
		for (InstHisManageVO row : list) {
			row.setPlntCd(getAuthUserPlntCd(token));
		}
		service.saveAsList(list);
		return ResponseEntity.ok(new CommonResponse());
	}

	@PutMapping
	public ResponseEntity<Integer> update(@AuthToken Token token, InstHisManageVO param) {
		param.setPlntCd(getAuthUserPlntCd(token));
		return ResponseEntity.ok(service.update(param));
	}

	@PutMapping("/delete")
	public ResponseEntity<CommonResponse> delete(@AuthToken Token token, @RequestBody InstHisManageVO param) {
		param.setPlntCd(getAuthUserPlntCd(token));
		service.delete(param);
		return ResponseEntity.ok(new CommonResponse());
	}

	@PutMapping("/requestApprove")
	public ResponseEntity<CommonResponse> requestApprove(@AuthToken Token token, @RequestBody InstHisManageVO param) {
		param.setPlntCd(getAuthUserPlntCd(token));
		service.requestApprove(param);
		return ResponseEntity.ok(new CommonResponse());
	}

	private String getAuthUserPlntCd(Token token) {
		String jwt = token.getJwt();
		return jwtResolver.getPlantCode(jwt);
	}

	private String getAuthUserId(Token token) {
		String jwt = token.getJwt();
		return jwtResolver.getUserId(jwt);
	}
}