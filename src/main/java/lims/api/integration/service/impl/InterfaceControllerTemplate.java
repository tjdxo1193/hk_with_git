package lims.api.integration.service.impl;

import lims.api.common.message.MessageUtil;
import lims.api.integration.enums.InterfaceResponseStatus;
import lims.api.integration.enums.RevInterface;
import lims.api.integration.model.InterfaceInfo;
import lims.api.integration.service.InterfaceErrorService;
import lims.api.integration.service.InterfaceInfoService;
import lims.api.integration.vo.IfInfoVO;
import lims.api.util.StringUtil;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterfaceControllerTemplate {

    private final InterfaceInfoService infoService;
    private final InterfaceErrorService errorService;

    public <T> T execute(RevInterface revInterface, InterfaceInfo info, Function<Integer, T> context, BiFunction<Exception, String, T> onError) {
        Integer infoIdx = null;
        try {
            info.setXifid(revInterface.getId());
            info.setXsysid(revInterface.getSystemTypeString());
            info.setIfType(revInterface.getSendType());
            infoIdx = infoService.createInfo(info.getRevInfoVO());
            return context.apply(infoIdx);

        } catch (NullPointerException e) {
            String message = MessageUtil.getMessage("error.notNull");
            log.error("[{}] Error during interface receive in interface controller. error message: {}", ThreadUtil.getCallerMethodName(), message);

            Integer errorLogId = errorService.record(infoIdx, e, message);
            updateErrorStatusInfo(infoIdx, errorLogId, message);
            return onError.apply(e, message);

        } catch (Exception e) {
            String message = StringUtil.substr(e.getMessage(), 120);
            log.error("[{}] Error during interface receive in interface controller. error message: {}", ThreadUtil.getCallerMethodName(), e.getMessage());

            Integer errorLogId = errorService.record(infoIdx, e);
            updateErrorStatusInfo(infoIdx, errorLogId, message);
            return onError.apply(e, message);
        }
    }

    private void updateErrorStatusInfo(Integer infoIdx, Integer errorLogId, String message) {
        if (infoIdx == null) {
            return;
        }
        IfInfoVO info = IfInfoVO.builder()
                .idx(infoIdx)
                .xstat(InterfaceResponseStatus.E)
                .xmsg(message)
                .errorLogId(errorLogId)
                .build();
        infoService.updateStatusInfo(info);
    }

}