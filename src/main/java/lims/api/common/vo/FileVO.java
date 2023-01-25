package lims.api.common.vo;

import lims.api.common.domain.UpdateDetect;
import lims.api.common.enums.DeleteType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileVO implements UpdateDetect {

    private String plntCd;
    private Integer fileIdx;
    private Integer fileSrlno;
    private String name;
    private Long size;
    private String type;
    private byte[] src;
    private DeleteType deleteAt = DeleteType.N;
    private Integer sortOrdr;
    private Integer fileSeq;
    private String udtDs;
    private String udtUid;
    private String udtNm;
}