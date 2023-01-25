package lims.api.sy.controller;

import lims.api.common.model.CommonResponse;
import lims.api.sy.service.PlantManageService;
import lims.api.sy.vo.PlantManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/sy/plantManage")
@RequiredArgsConstructor
public class PlantManageController {

    private final PlantManageService plantManageService;

    @GetMapping
    public ResponseEntity<List<PlantManageVO>> plants(PlantManageVO param) {
        return ResponseEntity.ok(plantManageService.getPlants(param));
    }

    @PostMapping
    public ResponseEntity<CommonResponse> create(@RequestBody PlantManageVO param) {
        plantManageService.create(param);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping
    public ResponseEntity<CommonResponse> update(@RequestBody PlantManageVO param) {
        plantManageService.update(param);
        return ResponseEntity.ok(new CommonResponse());
    }

}