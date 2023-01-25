package lims.api.common.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lims.api.auth.domain.Token;
import lims.api.auth.service.TokenHttpResolver;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.dao.AuditRecordDao;
import lims.api.common.domain.AuditRowData;
import lims.api.common.enums.PlantType;
import lims.api.common.enums.CUDType;
import lims.api.common.vo.AuditRecordData;
import lims.api.common.vo.AuditRecordDetailVO;
import lims.api.common.vo.AuditRecordVO;
import lims.api.common.vo.ESignVO;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.audit.type.CommandType;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditRecorder {

    private final TokenHttpResolver tokenHttpResolver;
    private final JwtResolver jwtResolver;
    private final AuditRecordDao recordDao;
    private final AuditESign auditESign;

    public void record(AuditRecordData recordData) {
        this.record(List.of(recordData));
    }

    public void record(List<AuditRecordData> recordDataList) {
        Integer auditIdx = getNextIdx();
        AuditRecordVO param = this.createAuditParam(auditIdx);

        int updatedCount = 0;
        updatedCount += recordDao.record(param);

        assertExistsCreatedAudit(updatedCount);

        int detailUpdatedCount = 0;

        for (AuditRecordData recordData : recordDataList) {
            AuditRecordDetailVO detailParam = createAuditDetailParam(auditIdx, recordData);
            detailUpdatedCount += recordDao.recordDetail(detailParam);
        }

        assertExistsCreatedAuditDetail(detailUpdatedCount);
    };

    private void assertExistsCreatedAudit(int updatedCount) {
        if (updatedCount == 0) {
            log.error("[{}] AuditTrail save was performed, but no exists saved data.", ThreadUtil.getCurrentMethodName());
            throw new RuntimeException("AuditTrail data no saved exception");
        }
    }

    private void assertExistsCreatedAuditDetail(int updatedCount) {
        if (updatedCount == 0) {
            log.error("[{}] AuditTrail save was performed, but no exists saved detail data.", ThreadUtil.getCurrentMethodName());
            throw new RuntimeException("AuditTrail detail data no saved exception");
        }
    }

    private AuditRecordVO createAuditParam(Integer auditIdx) {
        AuditRecordVO param = new AuditRecordVO();
        ESignVO eSign = getESign();
        param.setPlntCd(getPlantCode());
        param.setAuditIdx(auditIdx);
        param.setCrtIp(getClientIp());
        param.setCrtUid(getUserId());
        param.setMenuCd(getMenuCode());
        param.setEvtUrl(getRequestURI());
        param.setHttpMtd(getRequestMethod());
        param.setEleSgntYn(eSign.getSign());
        param.setRea(eSign.getReason());
        return param;
    }

    private AuditRecordDetailVO createAuditDetailParam(Integer auditIdx, AuditRecordData recordData) {
        AuditRecordDetailVO detailParam = new AuditRecordDetailVO();
        detailParam.setPlntCd(getPlantCode());
        detailParam.setAuditIdx(auditIdx);
        detailParam.setAuditSrlno(getNextSeqnoByIdx(auditIdx));
        detailParam.setEventNm(recordData.getLabel());
        detailParam.setCudDiv(convertCUDType(recordData.getCommandType()));
        detailParam.setChgBef(toJson(recordData.getBeforeData()));
        detailParam.setChgAft(toJson(recordData.getAfterData()));
        return detailParam;
    }

    private Integer getNextIdx() {
        return recordDao.nextIdx(getPlantCode());
    }

    private Integer getNextSeqnoByIdx(Integer auditIdx) {
        AuditRecordVO param = new AuditRecordVO();
        param.setPlntCd(getPlantCode());
        param.setAuditIdx(auditIdx);
        return recordDao.nextDetailSeq(param);
    }

    private String getPlantCode() {
        return isFromMenu() ? jwtResolver.getPlantCode(getJwt()) : PlantType.SYSTEM.getCode();
    }

    private boolean isFromMenu() {
        return HttpHelper.isExistsInRequestHeader(HttpHelper.MENU_CD_HEADER_NAME);
    }

    private String getUserId() {
        return jwtResolver.getUserId(getJwt());
    }

    private String getJwt() {
        Token token = tokenHttpResolver.getAccessToken();
        return token.getJwt();
    }

    private String getClientIp() {
        return HttpHelper.getClientIp();
    }

    private String getMenuCode() {
        return HttpHelper.getMenuCodeAtRequestHeader();
    }

    private String getRequestURI() {
        return HttpHelper.getHttpServletRequest().getRequestURI();
    }

    private String getRequestMethod() {
        return HttpHelper.getHttpServletRequest().getMethod();
    }

    private ESignVO getESign() {
        return auditESign.getESign();
    }

    private String toJson(AuditRowData auditRowData) {
        if (auditRowData == null) {
            return null;
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(convertKeyCaseToUpperCase(auditRowData));
    }

    private AuditRowData convertKeyCaseToUpperCase(AuditRowData auditRowData) {
        AuditRowData convertedAuditRowData = new AuditRowData();
        auditRowData.forEach((k, v) -> convertedAuditRowData.put(k.toUpperCase(), v));
        return convertedAuditRowData;
    }

    private CUDType convertCUDType(CommandType commandType) {
        return CUDType.of(commandType);
    }

}