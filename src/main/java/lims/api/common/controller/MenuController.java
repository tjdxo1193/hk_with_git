package lims.api.common.controller;

import lims.api.auth.annotation.Permit;
import lims.api.common.service.MenuService;
import lims.api.sy.vo.MenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @Permit
    @GetMapping
    public ResponseEntity<List<MenuVO>> menus() {
        return ResponseEntity.ok(menuService.getMenus());
    }

}