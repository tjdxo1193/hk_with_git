package lims.api.sy.service;

import lims.api.sy.model.AuditTrailSearchResponse;
import lims.api.sy.vo.AuditTrailSearchVO;

public interface AuditTrailSearchService {

    AuditTrailSearchResponse getAudits(AuditTrailSearchVO dto);

}
