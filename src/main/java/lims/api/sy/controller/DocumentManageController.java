package lims.api.sy.controller;

import lims.api.sy.service.DocumentService;
import lims.api.sy.vo.DocumentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/sy/document")
@RequiredArgsConstructor
public class DocumentManageController {

    private final DocumentService service;

    @GetMapping
    public ResponseEntity<List<DocumentVO>> documentList(DocumentVO request) {
        return ResponseEntity.ok(service.getAllDocuments(request));
    }

    @PostMapping
    public ResponseEntity<DocumentVO> create(DocumentVO request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping
    public ResponseEntity<DocumentVO> update(DocumentVO request) {
        return ResponseEntity.ok(service.update(request));
    }
}