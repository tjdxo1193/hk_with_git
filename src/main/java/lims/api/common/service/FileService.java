package lims.api.common.service;

import lims.api.common.domain.FileKey;
import lims.api.common.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    FileVO getFile(FileKey fileKey);

    List<FileVO> getAllFiles(Integer fileIdx);

    /**
     * fileIdx를 신규 생성하면서 저장할 때 사용합니다.
     * 이미 생성되어 있는 fileIdx에 파일을 저장할 때는 fileIdx를 인자로 받는 오버로딩 메서드를 사용하세요.
     */
    FileKey save(List<MultipartFile> files);

    FileKey save(List<MultipartFile> files, Integer fileIdx);

    void deleteFile(FileKey fileKey);

    void deleteAllFiles(Integer fileIdx);

}