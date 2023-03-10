package lims.api.sy.controller;

import lims.api.sy.service.InterfaceMasterService;
import lims.api.sy.vo.InterfaceMasterVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/sy/interface/masters")
@RequiredArgsConstructor
public class InterfaceMasterController {

    private final InterfaceMasterService masterService;

    @GetMapping
    public ResponseEntity<List<InterfaceMasterVO>> masters(InterfaceMasterVO param) {
        return ResponseEntity.ok(masterService.getMasters(param));
    }

}