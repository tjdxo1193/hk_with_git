package lims.api.np.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.np.service.PreventRecurrenceReviewService;
import lims.api.np.vo.PreventRecurrenceReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/np/preventRecurrenceReview")
public class PreventRecurrenceReviewController {

    private final PreventRecurrenceReviewService service;
    private final JwtResolver jwtResolver;


    @GetMapping
    public ResponseEntity<List<PreventRecurrenceReviewVO>> findAll(@AuthToken Token token, PreventRecurrenceReviewVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findAll(param));
    }

    @GetMapping("findResultItem")
    public ResponseEntity<List<PreventRecurrenceReviewVO>> findResultItem(@AuthToken Token token, PreventRecurrenceReviewVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findResultItem(param));
    }

    @PutMapping("/save")
    public ResponseEntity<CommonResponse> save(@RequestBody PreventRecurrenceReviewVO param) {
        service.save(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/request")
    public ResponseEntity<CommonResponse> request(@RequestBody PreventRecurrenceReviewVO param) {
        service.request(param);
        return ResponseEntity.ok(new CommonResponse());
    }
}
