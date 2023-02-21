package lims.api.common.controller;

import lims.api.common.service.FileService;
import lims.api.common.vo.FileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}//files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/{fileIdx}")
    public ResponseEntity<List<FileVO>> files(@PathVariable("fileIdx") Integer fileIdx) {
        return ResponseEntity.ok(fileService.getAllFiles(fileIdx));
    }

}