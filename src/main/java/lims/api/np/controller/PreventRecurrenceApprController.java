package lims.api.np.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.model.CommonResponse;
import lims.api.np.service.PreventRecurrenceApprService;
import lims.api.np.vo.PreventRecurrenceApprVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/np/preventRecurrenceAppr")
public class PreventRecurrenceApprController {

    private final PreventRecurrenceApprService service;
    private final JwtResolver jwtResolver;

    @GetMapping
    public ResponseEntity<List<PreventRecurrenceApprVO>> findAll(@AuthToken Token token, PreventRecurrenceApprVO param) {
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        param.setUserId(jwtResolver.getUserId(jwt));
        return ResponseEntity.ok(service.findAll(param));
    }

    @GetMapping("findResultItem")
    public ResponseEntity<List<PreventRecurrenceApprVO>> findResultItem(@AuthToken Token token, PreventRecurrenceApprVO param){
        String jwt = token.getJwt();
        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.findResultItem(param));
    }

    @PutMapping("/approve")
    public ResponseEntity<CommonResponse> approve(@RequestBody PreventRecurrenceApprVO param) {
        service.approve(param);
        return ResponseEntity.ok(new CommonResponse());
    }
}
