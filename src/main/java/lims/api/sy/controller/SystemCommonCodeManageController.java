package lims.api.sy.controller;

import lims.api.sy.service.SystemCommonCodeManagementService;
import lims.api.sy.vo.SystemCommonCodeManagementVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/sy/systemCommonCodeManagement")
@RequiredArgsConstructor
public class SystemCommonCodeManageController {

    private final SystemCommonCodeManagementService service;

    @PostMapping("getSyCmCodeHir")
    public ResponseEntity<List<SystemCommonCodeManagementVO>> getSyCmCodeHir(@RequestBody SystemCommonCodeManagementVO request){
        return ResponseEntity.ok(service.getSyCmCodeHir(request));
    }

    @PostMapping("getSyCmCodeDtl")
    public ResponseEntity<List<SystemCommonCodeManagementVO>> getSyCmCodeDtl(@RequestBody SystemCommonCodeManagementVO request){
        return ResponseEntity.ok(service.getSyCmCodeDtl(request));
    }
}