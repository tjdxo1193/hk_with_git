package lims.api.ms.controller;

import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.ms.service.TestItemPerManageService;
import lims.api.ms.vo.TestItemPerManageVO;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ejb.DuplicateKeyException;
import java.util.List;
@Slf4j
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

	@PostMapping("elnSendTestItemMethod")
	public ResponseEntity<CommonResponse> elnSendTestItemMethod(@RequestBody TestItemPerManageVO param) {
		try {
			if (testItemPerManageService.alreadySentToELNOrNot(param)){
				throw new DuplicateKeyException("시험항목방법코드가 이미 전송되었습니다.");
			}
			testItemPerManageService.elnSendTestItemMethod(param);
		} catch(
		DuplicateKeyException e) {
			log.error("[{}] The requested value is duplicated. AMITM_CD = [{}]. Please request again with a different value. ", ThreadUtil.getCurrentMethodName(), param.getAmitmCd());
			return ResponseEntity.ok( new CommonResponse(false, e.getMessage()));
		}

		return ResponseEntity.ok( new CommonResponse());
	}
}