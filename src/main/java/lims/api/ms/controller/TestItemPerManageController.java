package lims.api.ms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lims.api.auth.service.impl.JwtResolver;
import lims.api.ms.service.TestItemPerManageService;
import lims.api.ms.vo.TestItemPerManageVO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ms/testItemPerManage")
public class TestItemPerManageController {
	private final TestItemPerManageService testItemPerManageService;
	private final JwtResolver jwtResolver;
	
	@GetMapping
	public ResponseEntity<List<TestItemPerManageVO>> getList(TestItemPerManageVO param) {
        return ResponseEntity.ok(testItemPerManageService.getList(param));
    }
	
	@GetMapping("getPerList")
	public ResponseEntity<List<TestItemPerManageVO>> getPerList(TestItemPerManageVO param) {
		return ResponseEntity.ok(testItemPerManageService.getPerList(param));
	}
	
	@PostMapping("putTestItemPer")
	public ResponseEntity<Integer> putTestItemPer(@RequestBody TestItemPerManageVO param) {
		return ResponseEntity.ok(testItemPerManageService.putTestItemPer(param));
	}
}