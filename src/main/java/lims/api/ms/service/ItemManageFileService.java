package lims.api.ms.service;

import lims.api.ms.domain.MsElnCtRptFileKey;
import lims.api.ms.vo.MsElnCtRptFileVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemManageFileService {
    List<MsElnCtRptFileVO> getMsElnCtRptFileList(MsElnCtRptFileVO param);
    MsElnCtRptFileKey save(List<MultipartFile> files);

    MsElnCtRptFileKey save(List<MultipartFile> files, String ctId);

    void deleteFile(MsElnCtRptFileKey removedFile);
}
