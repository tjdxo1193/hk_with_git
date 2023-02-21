package lims.api.sy.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class NoticeManagementVO {
    private Integer ntbIdx;			// 게시판 IDX
    private String ntbDiv;			// 게시판 구분
    private String ntbTit;			// 게시판 제목
    private String ntbCtt;			// 게시판 내용
    private String popYn;			// 팝업 여부
    private String popTrmEnd;		// 팝업 종료 일자
    private String popTrmStr;		// 팝업 시작 일자
    private Integer fileIdx;		// 첨부파일 IDX
    private String udtDs;			// 수정 일시
    private String udtUid;			// 수정자UID
    private String delYn;			// 삭제 여부
    private ArrayList<String> popupPeriod; //팝업 기간
    private String spopupPeriod;
}