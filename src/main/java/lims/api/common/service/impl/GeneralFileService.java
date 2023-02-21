package lims.api.common.service.impl;

import lims.api.auth.domain.Token;
import lims.api.auth.service.TokenHttpResolver;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.dao.FileDao;
import lims.api.common.domain.FileKey;
import lims.api.common.service.FileService;
import lims.api.common.enums.DeleteType;
import lims.api.common.vo.FileVO;
import lims.api.util.FileUtil;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneralFileService implements FileService {

    private final FileDao fileDao;
    private final TokenHttpResolver tokenHttpResolver;
    private final JwtResolver jwtResolver;

    @Override
    public FileVO getFile(FileKey fileKey) {
        FileVO param = new FileVO();
        param.setPlntCd(getPlantCode());
        param.setFileIdx(fileKey.getFileIdx());
        param.setFileSrlno(fileKey.getFileSrlno());
        return fileDao.findOne(param);
    }

    @Override
    public List<FileVO> getAllFiles(Integer fileIdx) {
        FileVO param = new FileVO();
        param.setPlntCd(getPlantCode());
        param.setFileIdx(fileIdx);
        return fileDao.findAll(param);
    }

    @Override
    public FileKey save(List<MultipartFile> files) {
        return this.save(files, fileDao.nextIdx(getPlantCode()));
    }

    @Override
    public FileKey save(List<MultipartFile> files, Integer fileIdx) {
        FileKey fileKey = new FileKey(fileIdx);

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);

            FileVO seqParam = new FileVO();
            seqParam.setPlntCd(getPlantCode());
            seqParam.setFileIdx(fileIdx);
            Integer fileSeq = fileDao.nextSeq(seqParam);

            FileVO param = new FileVO();
            param.setPlntCd(getPlantCode());
            param.setFileIdx(fileIdx);
            param.setFileSrlno(fileSeq);
            param.setDeleteAt(DeleteType.N);
            param.setName(file.getOriginalFilename());
            param.setType(file.getContentType());
            param.setSize(file.getSize());
            param.setSortOrdr(i + 1);
            param.setSrc(FileUtil.toBytes(file));

            int savedCount = fileDao.save(param);

            if (savedCount == 0) {
                throw new RuntimeException("Not saved file" + file.getName());
            }
        }
        return fileKey;
    }

    @Override
    public void deleteFile(FileKey fileKey) {
        FileVO param = new FileVO();
        param.setPlntCd(getPlantCode());
        param.setFileIdx(fileKey.getFileIdx());
        param.setFileSrlno(fileKey.getFileSrlno());
        int deletedCount = fileDao.deleteOne(param);
        
        if (deletedCount == 0) {
            throw new RuntimeException("Failed delete a file. fileIdx: " + fileKey.getFileIdx() + ", fileSrlno: " + fileKey.getFileSrlno());
        }
    }

    @Override
    public void deleteAllFiles(Integer fileIdx) {
        FileVO param = new FileVO();
        param.setPlntCd(getPlantCode());
        param.setFileIdx(fileIdx);
        int deletedCount = fileDao.deleteAll(param);

        if (deletedCount == 0) {
            throw new RuntimeException("Failed delete all files. fileIdx: " + fileIdx);
        }
    }

    private String getPlantCode() {
        Token token = tokenHttpResolver.getAccessToken();
        return jwtResolver.getPlantCode(token.getJwt());
    }

}