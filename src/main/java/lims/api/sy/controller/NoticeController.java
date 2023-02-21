package lims.api.sy.controller;

import lims.api.sy.service.NoticeService;
import lims.api.sy.vo.NoticeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/sy/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public ResponseEntity<List<NoticeVO>> findAll(NoticeVO vo) {
        return ResponseEntity.ok(noticeService.findAll(vo));
    }

}