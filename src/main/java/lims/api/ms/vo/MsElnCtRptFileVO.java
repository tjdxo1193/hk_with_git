package lims.api.ms.vo;

import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MsElnCtRptFileVO implements UpdateDetect {
    private String ctId;
    private String ctSeq;
    private String fileName;
    private byte[] data;
    private String crtDs;
    private String udtDs;
    private String crtDt;
    private String udtDt;
//    private String fileType;

    private String ctrptNo;                     // ctId, 조회용
    private String ctSeqOrd;                    // 정렬용
    private String name;                        // 파일명(DropZone용 임의 변수)
    private String type;                        // 파일타입(DropZone용 임의 변수)
    private Long size;                          // 파일크기(DropZone용 임의 변수)
    private byte[] src;                         // 파일(DropZone용 임의 변수)
    private Integer fileIdx;                    // 파일 idx(DropZone용 임의 변수)
    private Integer fileSrlno;                    // 파일 seq(DropZone용 임의 변수)
}
