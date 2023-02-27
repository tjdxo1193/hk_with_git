package lims.api.ts.vo;

import lims.api.common.domain.FileKey;
import lims.api.common.domain.UpdateDetect;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TestIFModalVO implements UpdateDetect {
    private String plntCd; //사업장 코드

    /*마감오더*/
    private String phsOrderNo;
    private String pdtOrderNo;
    private String orderItm;
    private String lotNo; //제조번호
    private String finlStt;
    private String crtDs;
    private Integer ifInfoIdx;
    /*의뢰정보*/
    private String phsOrderTyp; //구매 오더 유형
    private String pdtOrderTyp; //생산 오더 유형
    private String phsOrderItm; //구매 오더 항목
    private String ispReqNo; //검사 요청 번호
    private String ansNo; //시험번호
    private String ansProcNm; //진행상태
    private String sytJdgNm; //판정결과
    private String hldYn; //보류여부
    private String pitmCd; //품목코드
    private String pitmNm; //품목명
    private String pitmTypNm; //품목유형

    /*재발방지대책서,SRM성적서,MES포장사양서*/
    private String batchNo;
    private String makNo;
    private String name;
    private Integer fileIdx;
    private Integer seq;
    private Integer fileSrlno;
    private String fileId;
    private byte[] src;
    private String type;
    private Long size;
    private String rptDiv;
    private Integer ver;
    private String delYn;

    /*투입실적*/
    private String mtrCd;
    private String inpMtrCd;
    private String inpBatchNo;
    private String mtrNm;
    private String mrpMng;
    private String bwart;
    private String erfmg;
    private String erfme;
    private String lgort;
    private String budat;
    private String zexfield1;
    private String zexfield2;
    private String zexfield3;
    private String zexfield4;
    private String zexfield5;

    //추가 파일
    private List<MultipartFile> addedFiles = new ArrayList<>();
    private List<FileKey> removedFileIds = new ArrayList<>();

}
