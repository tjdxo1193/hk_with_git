package lims.api.integration.service.impl;

import lims.api.common.message.MessageUtil;
import lims.api.integration.enums.*;
import lims.api.integration.service.InterfaceErrorService;
import lims.api.integration.service.InterfaceInfoService;
import lims.api.integration.vo.IfInfoVO;
import lims.api.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class RfcTemplate {

    private final InterfaceInfoService infoService;
    private final InterfaceErrorService interfaceErrorServiceImpl;

    public void execute(RfcInterface rfc, Consumer<Integer> context) {
        IfInfoVO infoVO = createRfcInfoVO(rfc);
        Integer infoIdx = null;
        try {
            infoIdx = infoService.createInfo(infoVO);
            infoVO.setIdx(infoIdx);

            context.accept(infoVO.getIdx());
            updateSuccessStatusInfo(infoVO);
        } catch (NullPointerException e) {
            String message = MessageUtil.getMessage("error.notNull");
            log.error("[RfcTemplate] Error during call RFC. error message: {}", message);

            Integer errorLogId = interfaceErrorServiceImpl.record(infoIdx, e, message);
            updateErrorStatusInfo(infoVO, errorLogId, message);

        } catch (DataAccessException e) {
            String message = MessageUtil.getMessage("error.sql.interfaceData");
            log.error("[RfcTemplate] Error during call RFC. error message: {}", e.getMessage());

            Integer errorLogId = interfaceErrorServiceImpl.record(infoIdx, e);
            updateErrorStatusInfo(infoVO, errorLogId, message);
        }  catch (Exception e) {
            String message = StringUtil.substr(e.getMessage(), 120);
            log.error("[RfcTemplate] Error during interface receive in interface controller. error message: {}", e.getMessage());

            Integer errorLogId = interfaceErrorServiceImpl.record(infoIdx, e);
            updateErrorStatusInfo(infoVO, errorLogId, message);
        }

    }

    private IfInfoVO createRfcInfoVO(RfcInterface rfc) {
        return IfInfoVO.builder()
                .xsysid(InterfaceSystemType.SAP.name())
                .xifid(rfc.getFunctionName())
                .xdate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .xtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss")))
                .ifType(InterfaceSendType.SYNC)
                .infoDiv(InterfaceInfoDiv.RFC)
                .build();
    }

    private void updateSuccessStatusInfo(IfInfoVO infoVO) {
        IfInfoVO info = IfInfoVO.builder()
                .idx(infoVO.getIdx())
                .xsysid(infoVO.getXsysid())
                .xifid(infoVO.getXifid())
                .xstat(InterfaceResponseStatus.S)
                .xmsg(InterfaceResponseStatus.S.getMessage())
                .ifType(infoVO.getIfType())
                .infoDiv(infoVO.getInfoDiv())
                .xdate(infoVO.getXdate())
                .xtime(infoVO.getXtime())
                .build();
        infoService.updateStatusInfo(info);
    }

    private void updateErrorStatusInfo(IfInfoVO infoVO, Integer errorLogId, String message) {
        if (infoVO.getIdx() == null) {
            return;
        }
        IfInfoVO info = IfInfoVO.builder()
                .idx(infoVO.getIdx())
                .xsysid(infoVO.getXsysid())
                .xifid(infoVO.getXifid())
                .xstat(InterfaceResponseStatus.E)
                .xmsg(message)
                .ifType(infoVO.getIfType())
                .infoDiv(infoVO.getInfoDiv())
                .xdate(infoVO.getXdate())
                .xtime(infoVO.getXtime())
                .errorLogId(errorLogId)
                .build();
        infoService.updateStatusInfo(info);
    }

}