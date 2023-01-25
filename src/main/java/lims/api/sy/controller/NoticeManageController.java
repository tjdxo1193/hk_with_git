package lims.api.sy.controller;
import lims.api.sy.service.NoticeManagementService;
import lims.api.sy.vo.NoticeManagementVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/sy/noticeManagement")
@RequiredArgsConstructor
public class NoticeManageController {

    private final NoticeManagementService service;

    @GetMapping
    public ResponseEntity<List<NoticeManagementVO>> findAll(NoticeManagementVO vo) {
        return ResponseEntity.ok(service.findAll(vo));
    }

    @PostMapping
    public ResponseEntity<NoticeManagementVO> create(NoticeManagementVO request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping
    public ResponseEntity<NoticeManagementVO> update(NoticeManagementVO request) {
        return ResponseEntity.ok(service.update(request));
    }


}