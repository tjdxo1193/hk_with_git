package lims.api.ms.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lims.api.ms.service.TestItemManageService;
import lims.api.ms.vo.TestItemManageVO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ms/testItemManage")
public class TestItemManageController {
	private final TestItemManageService testItemManageService;
	
	@GetMapping
	public ResponseEntity<List<TestItemManageVO>> getList(TestItemManageVO param) {
        return ResponseEntity.ok(testItemManageService.getList(param));
    }
	
	@PostMapping("putTestItem")
	@Transactional
	public ResponseEntity<Integer> putTestItem(@RequestBody TestItemManageVO param) {
        return ResponseEntity.ok(testItemManageService.putTestItem(param));
    }
}