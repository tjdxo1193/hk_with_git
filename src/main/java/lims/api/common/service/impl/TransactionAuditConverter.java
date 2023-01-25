package lims.api.common.service.impl;

import lims.api.common.domain.AuditColumnData;
import lims.api.common.domain.AuditRowData;
import lims.api.common.vo.AuditRecordData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.audit.domain.AuditTrail;
import spring.audit.domain.SqlRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionAuditConverter {

    public List<AuditRecordData> convert(AuditTrail auditTrail) {
        List<AuditRowData> beforeRows = toAuditColumnList(auditTrail.getOriginData());
        List<AuditRowData> afterRows = toAuditColumnList(auditTrail.getUpdatedData());

        int len = Math.max(beforeRows.size(), afterRows.size());
        List<AuditRecordData> result = new ArrayList<>();

        for (int i=0; i < len; i++) {
            AuditRecordData vo = toDetailVO(auditTrail);

            if (i < beforeRows.size()) {
                vo.setBeforeData(beforeRows.get(i));
            }
            if (i < afterRows.size()) {
                vo.setAfterData(afterRows.get(i));
            }
            result.add(vo);
        }
        return result;
    }

    private List<AuditRowData> toAuditColumnList(List<SqlRow> sqlRows) {
        return sqlRows.stream().map(sqlRow -> {
            AuditRowData auditRowData = new AuditRowData();
            sqlRow.forEach((columnNm, sqlColumn) -> {
                auditRowData.put(columnNm, new AuditColumnData(sqlColumn.getData(), sqlColumn.getComment()));
            });
            return auditRowData;
        }).collect(Collectors.toList());
    }

    private AuditRecordData toDetailVO(AuditTrail auditTrail) {
        AuditRecordData vo = new AuditRecordData();
        vo.setLabel(auditTrail.getLabel());
        vo.setCommandType(auditTrail.getCommandType());
        return vo;
    }

}