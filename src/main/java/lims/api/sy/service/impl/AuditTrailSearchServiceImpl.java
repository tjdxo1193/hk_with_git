package lims.api.sy.service.impl;

import lims.api.common.domain.GridHeader;
import lims.api.sy.dao.AuditTrailManageDao;
import lims.api.sy.dao.AuditTrailSearchDao;
import lims.api.sy.model.AuditTrailSearchResponse;
import lims.api.sy.service.AuditTrailSearchService;
import lims.api.sy.vo.AuditMaster;
import lims.api.sy.vo.AuditTrailSearchVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditTrailSearchServiceImpl implements AuditTrailSearchService {

    private final AuditTrailManageDao manageDao;
    private final AuditTrailSearchDao searchDao;

    private final List<GridHeader> preFixedHeaders = List.of(
            new GridHeader("PLNT_CD", "사업장 코드"),
            new GridHeader("MENU_NM", "메뉴명"),
            new GridHeader("EVT_NM", "이벤트명")
    );

    private final List<GridHeader> postFixedHeaders = List.of(
            new GridHeader("CHG_BEF", "변경 전"),
            new GridHeader("CHG_AFT", "변경 후"),
            new GridHeader("REA", "사유"),
            new GridHeader("CRT_USER_NM", "사용자명"),
            new GridHeader("CRT_IP", "IP"),
            new GridHeader("EVT_URL", "URL"),
            new GridHeader("CRT_DS", "날짜")
    );

    @Override
    public AuditTrailSearchResponse getAudits(AuditTrailSearchVO param) {
        List<AuditMaster> masters = manageDao.findAuditMasterByMenuCode(param.getMenuCd());
        return new AuditTrailSearchResponse(getGridHeaders(masters), getData(
                masters, param));
    }

    private List<GridHeader> getGridHeaders(List<AuditMaster> masters) {
        List<GridHeader> masterHeaders = masters.stream()
                .map(master -> new GridHeader(master.getColumnNm(), master.getColumnComment()))
                .collect(Collectors.toList());

        List<GridHeader> headers = new ArrayList<>(preFixedHeaders);
        headers.addAll(masterHeaders);
        headers.addAll(new ArrayList<>(postFixedHeaders));
        return headers;
    }

    private List<Map<String, Object>> getData(List<AuditMaster> masters, AuditTrailSearchVO param) {
        param.setSelectClauseWithMasters(createSelectClauseWithMasters(masters));
        return searchDao.findAuditsByPlantCodeAndMenuCode(param);
    }

    /**
     * Oracle Json 문법의 경로 표현식은 리터럴이어야 하므로 Mybatis로 동적인 사용이 불가능합니다.
     * 따라서, 여기서 쿼리를 생성합니다.
     */
    private String createSelectClauseWithMasters(List<AuditMaster> masters) {
        if (CollectionUtils.isEmpty(masters)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (AuditMaster master : masters) {
            sb.append(hasJoinColumn(master) ? createColumnQuery(master) : createScalarSubQuery(master)).append(",");
        }
        return "," + StringUtils.chop(sb.toString());
    }

    //-> `JSON_VALUE( AD.AFTER_DATA, '$.{COLUMN_NM}.data') AS {COLUMN_NM}
    private StringBuilder createColumnQuery(AuditMaster master) {
        return new StringBuilder().append(createGetJsonValueQuery(master.getColumnNm())).append(" AS ").append(master.getColumnNm());
    }

    //-> `( SELECT {JOIN_DISPLAY_COL_NM} FROM {JOIN_TAB_NM} WHERE {JOIN_COL_NM} = JSON_VALUE( AD.AFTER_DATA, '$.{COLUMN_NM}.data') ) AS {COLUMN_NM}
    private StringBuilder createScalarSubQuery(AuditMaster master) {
        return new StringBuilder()
                .append("( SELECT ")
                .append(master.getJoinDisplayColumnNm())
                .append(" FROM ")
                .append(master.getJoinTableNm())
                .append(" WHERE ")
                .append(master.getJoinColumnNm())
                .append(" = ")
                .append(createGetJsonValueQuery(master.getColumnNm()))
                .append(" ) AS ")
                .append(master.getColumnNm());
    }

    private String createGetJsonValueQuery(String columnNm) {
        return " JSON_VALUE( AD.AFTER_DATA, '$." + columnNm + ".data') ";
    }

    private boolean hasJoinColumn(AuditMaster master) {
        return Strings.isEmpty(master.getJoinColumnNm());
    }
}