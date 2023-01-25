package lims.api.sy.controller;

import lims.api.sy.service.ScheduleManageService;
import lims.api.sy.vo.ScheduleManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/sy/scheduleManage")
public class ScheduleManageController {
    private final ScheduleManageService service;

    @GetMapping("list")
    public ResponseEntity<List<ScheduleManageVO>> getList(ScheduleManageVO param){
        return ResponseEntity.ok(service.getList(param));
    }
}