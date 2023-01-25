package lims.api.sy.vo;

import lims.api.common.domain.FileKey;
import lims.api.common.domain.UpdateDetect;
import lims.api.common.enums.DeleteType;
import lims.api.common.vo.FileVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DocumentVO implements UpdateDetect {

    private Integer ntbIdx;
    private String ntbTit;
    private DeleteType delYn;
    private String ntbCtt;
    private String udtUid;
    private List<MultipartFile> addedFiles = new ArrayList<>();
    private List<FileKey> removedFileIds = new ArrayList<>();

    private List<FileVO> files;
    private Integer fileIdx;

}