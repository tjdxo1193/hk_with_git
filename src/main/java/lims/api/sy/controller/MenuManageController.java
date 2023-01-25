package lims.api.sy.controller;

import lims.api.common.model.CommonResponse;
import lims.api.sy.service.MenuManageService;
import lims.api.sy.vo.MenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sy/menuManage")
public class MenuManageController {

    private final MenuManageService service;

    @GetMapping
    public ResponseEntity<List<MenuVO>> findAll(MenuVO request) {
        return ResponseEntity.ok(service.findAll(request));
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@RequestBody MenuVO request) {
        service.update(request);
        return ResponseEntity.ok(new CommonResponse());
    }

}