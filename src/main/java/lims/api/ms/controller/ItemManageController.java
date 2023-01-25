package lims.api.ms.controller;

import lims.api.auth.annotation.AuthToken;
import lims.api.auth.domain.Token;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.domain.PagingResult;
import lims.api.common.model.CommonResponse;
import lims.api.ms.service.ItemManageService;
import lims.api.ms.vo.ItemManageVO;
import lims.api.ms.vo.MsElnCtRptFileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/ms/itemManage")
public class ItemManageController {
    private final ItemManageService service;
    private final JwtResolver jwtResolver;

    @GetMapping("/pItem")
    public ResponseEntity<PagingResult<ItemManageVO>> findPItem(ItemManageVO request) {
        return ResponseEntity.ok(service.findPItem(request));
    }

    @GetMapping("/version")
    public ResponseEntity<List<ItemManageVO>> findVersion(ItemManageVO request) {
        return ResponseEntity.ok(service.findVersion(request));
    }

    @PutMapping("/tempSave")
    public ResponseEntity<CommonResponse> tempSave(@AuthToken Token token, @RequestBody ItemManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        service.tempSave(request);
        return ResponseEntity.ok(new CommonResponse());
    }
    
    @PostMapping("/revision")
    public ResponseEntity<CommonResponse> revision(@AuthToken Token token, @RequestBody ItemManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        service.revision(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @PutMapping("/firstSave")
    public ResponseEntity<CommonResponse> firstSave(@AuthToken Token token, @RequestBody ItemManageVO request){
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        service.firstSave(request);
        return ResponseEntity.ok(new CommonResponse());
    }

    @GetMapping("/getFileList/{ctrptNo}")
    public ResponseEntity<List<MsElnCtRptFileVO>> getFileList(@PathVariable String ctrptNo) {
        return ResponseEntity.ok(service.getFileList(ctrptNo));
    }

    @PostMapping("/saveFile")
    public ResponseEntity<String> savedFile(@AuthToken Token token, ItemManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.savedFile(request));
    }

    @GetMapping("/getBomList")
    public ResponseEntity<List<ItemManageVO>> getBomList(@AuthToken Token token, ItemManageVO request) {
        String jwt = token.getJwt();
        request.setPlntCd(jwtResolver.getPlantCode(jwt));
        return ResponseEntity.ok(service.getBomList(request));
    }
}