package lims.api.sy.service.impl;

import lims.api.common.domain.FileKey;
import lims.api.common.service.FileService;
import lims.api.sy.dao.DocumentDao;
import lims.api.sy.service.DocumentService;
import lims.api.sy.vo.DocumentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentDao documentDao;
    private final FileService fileService;

    @Override
    public List<DocumentVO> getAllDocuments(DocumentVO param) {
        return documentDao.findAll(param);
    }

    @Override
    public DocumentVO create(DocumentVO vo) {
        FileKey fileKey = fileService.save(vo.getAddedFiles());
        Integer ntbIdx = documentDao.nextIdx();
        vo.setNtbIdx(ntbIdx);
        vo.setFileIdx(fileKey.getFileIdx());
        documentDao.create(vo);

        vo.setAddedFiles(null);
        vo.setFiles(fileService.getAllFiles(fileKey.getFileIdx()));
        return vo;
    }

    @Override
    public DocumentVO update(DocumentVO vo) {
        for (FileKey removedFileId : vo.getRemovedFileIds()) {
            fileService.deleteFile(removedFileId);
        }

        fileService.save(vo.getAddedFiles(), vo.getFileIdx());
        documentDao.update(vo);

        vo.setAddedFiles(null);
        vo.setRemovedFileIds(null);
        vo.setFiles(fileService.getAllFiles(vo.getFileIdx()));
        return vo;
    }

}