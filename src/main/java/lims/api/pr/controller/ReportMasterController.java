package lims.api.pr.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.pr.service.ReportMasterService;
import lims.api.pr.vo.ReportMasterVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/pr/reportMaster")
public class ReportMasterController {

    private final ReportMasterService reportMasterService;

    private final JwtResolver jwtResolver;

    // 리포트마스터 조회
    @GetMapping
    public List<ReportMasterVO> findAll(ReportMasterVO reportMasterVO) {
        return reportMasterService.findAll(reportMasterVO);
    }

    // 리포트마스터 저장
    @PutMapping
    public int save(@AuthToken Token token, @RequestBody ReportMasterVO reportMasterVO) {
        reportMasterVO.setLoginUserUid(this.getAuthUserId(token));
        return reportMasterService.save(reportMasterVO);
    }
    
    // 리포트마스터 삭제
    @PostMapping
    public int delete(@AuthToken Token token, @RequestBody ReportMasterVO reportMasterVO) {
        reportMasterVO.setLoginUserUid(this.getAuthUserId(token));
        return reportMasterService.delete(reportMasterVO);
    }

    private String getAuthUserId(Token token) {
        String jwt = token.getJwt();
        return jwtResolver.getUserId(jwt);
    }
}
