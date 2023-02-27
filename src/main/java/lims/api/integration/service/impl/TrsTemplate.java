package lims.api.integration.service.impl;

import lims.api.integration.domain.eai.TrsEventHandler;
import lims.api.integration.domain.eai.TrsResult;
import lims.api.integration.domain.eai.TrsStateful;
import lims.api.integration.enums.InterfaceInfoDiv;
import lims.api.integration.enums.InterfaceResponseStatus;
import lims.api.integration.enums.InterfaceTrsStatus;
import lims.api.integration.enums.TrsInterface;
import lims.api.integration.exception.IntegrationResponseException;
import lims.api.integration.model.InterfaceTrsResponse;
import lims.api.integration.service.InterfaceErrorService;
import lims.api.integration.service.InterfaceInfoService;
import lims.api.integration.service.TrsService;
import lims.api.integration.vo.IfInfoVO;
import lims.api.util.StringUtil;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
@Slf4j

@Service
@RequiredArgsConstructor
public class TrsTemplate implements TrsService {

    private final InterfaceInfoService infoService;
    private final InterfaceErrorService errorService;

    @Override
    public <T extends TrsStateful> TrsResult execute(TrsInterface trsInterface, T recordData, Supplier<Integer> degreeSupplier, Supplier<Integer> idxSupplier, TrsEventHandler<T> process) {
        return execute(trsInterface, List.of(recordData), degreeSupplier, idxSupplier, process);
    }

    @Override
    public <T extends TrsStateful> TrsResult execute(TrsInterface trsInterface, List<T> recordData, Supplier<Integer> degreeSupplier, Supplier<Integer> idxSupplier, TrsEventHandler<T> process) {
        List<T> copiedData = copyList(recordData);
        Integer infoIdx = createTrsInfo(trsInterface);
        int degree = 0;

        try {
            degree = degreeSupplier.get();

            executeOnPending(process, copiedData, degree, idxSupplier);

            InterfaceTrsResponse response = process.send();
            log.info("--- Trs Response ---");
            log.info(response.toString());

            assertNotErrorResponse(response);
            executeOnSuccess(infoIdx, process, copiedData, response);
            updateSuccessStatusInfoBySync(infoIdx, response.getTrsInfoVO());

            return createTrsSuccessResult(infoIdx, degree);
        } catch (IntegrationResponseException e) {
            log.error("[{} - {}] Receive response with Error status of interface request. {}", trsInterface.name(), trsInterface.getId(), e.getMessage());

            /**
             * IntegrationResponseException 예외는
             * EAI 연계를 통해 응답을 받은 뒤 상태값이 에러인 경우에 발생합니다.
             * 따라서, infoIdx가 존재합니다.
             */
            String message = StringUtil.substr(e.getMessage(), 120);
            Integer errorLogId = executeOnError(infoIdx, process, copiedData, e);
            updateErrorStatusInfo(infoIdx, errorLogId, message);
            return createTrsErrorResult(infoIdx, degree);
        } catch (Exception e) {
            log.error("[{} - {}] Failed send integration interface request. {}", trsInterface.name(), trsInterface.getId(), e.getMessage());

            String message = StringUtil.substr(e.getMessage(), 120);
            Integer errorLogId = executeOnError(infoIdx, process, copiedData, e);
            updateErrorStatusInfo(infoIdx, errorLogId, message);
            return createTrsErrorResult(infoIdx, degree);
        }
    }

    @Override
    public <T extends TrsStateful> void executeAsync(TrsInterface trsInterface, List<T> recordData, Supplier<Integer> degreeSupplier, Supplier<Integer> idxSupplier, TrsEventHandler<T> process) {
        List<T> copiedData = copyList(recordData);
        Integer infoIdx = createTrsInfo(trsInterface);

        try {
            int degree = degreeSupplier.get();

            executeOnPending(process, copiedData, degree, idxSupplier);

            process.send();

            executeOnSuccess(infoIdx, process, copiedData);
            updateSuccessStatusInfoByAsync(infoIdx);
        } catch (Exception e) {
            log.error("[{}] Failed send integration interface request. {}", ThreadUtil.getCurrentMethodName(), e.getMessage());
            executeOnError(infoIdx, process, copiedData, e);
        }
    }

    private Integer createTrsInfo(TrsInterface trsInterface) {
        IfInfoVO infoVO = IfInfoVO.builder()
                .xsysid(trsInterface.getSystemTypeString())
                .xifid(trsInterface.getId())
                .ifType(trsInterface.getSendType())
                .infoDiv(InterfaceInfoDiv.TRS)
                .build();
        return infoService.createInfo(infoVO);
    }

    private <T extends TrsStateful> void executeOnPending(TrsEventHandler<T> process, List<T> list, Integer degree, Supplier<Integer> idxSupplier) {
        for (T t : list) {
            t.setDegree(degree);
            t.setIdx(idxSupplier.get());
            t.setTrsStatus(InterfaceTrsStatus.PENDING);
            process.saveBeforeSend(t);
        }
    }

    private <T extends TrsStateful> void executeOnSuccess(Integer infoIdx, TrsEventHandler<T> process, List<T> list) {
        executeOnSuccess(infoIdx, process, list, null);
    }

    private <T extends TrsStateful> void executeOnSuccess(Integer infoIdx, TrsEventHandler<T> process, List<T> list, InterfaceTrsResponse response) {
        for (T t : list) {
            t.setIfInfoIdx(infoIdx);
            t.setTrsStatus(InterfaceTrsStatus.SUCCESS);
            process.saveAfterSend(t, response);
        }
    }

    private <T extends TrsStateful> Integer executeOnError(Integer infoIdx, TrsEventHandler<T> process, List<T> list, Exception e) {
        int errorId = errorService.record(infoIdx, e);

        for (T t : list) {
            if (t.getIdx() == null) {
                continue;
            }
            t.setIfInfoIdx(infoIdx);
            t.setErrorlogId(errorId);
            t.setTrsStatus(InterfaceTrsStatus.FAILED);
            process.saveOnError(t);
        }
        return errorId;
    }

    @SuppressWarnings("unchecked")
    private <T extends TrsStateful> List<T> copyList(List<T> list) {
        List<T> copied = new ArrayList<>();
        for (T t : list) {
            copied.add((T) t.clone());
        }
        return copied;
    }

    private void assertNotErrorResponse(InterfaceTrsResponse response) {
        if (response.getXstat().isError()) {
            throw new IntegrationResponseException(response.getXmsg());
        }
    }

    private void updateSuccessStatusInfoBySync(Integer infoIdx, IfInfoVO infoVO) {
        IfInfoVO info = IfInfoVO.builder()
                .idx(infoIdx)
                .xtid(infoVO.getXtid())
                .xdate(infoVO.getXdate())
                .xtime(infoVO.getXtime())
                .xstat(InterfaceResponseStatus.S)
                .xmsg(InterfaceResponseStatus.S.getMessage())
                .build();
        infoService.updateStatusInfo(info);
    }

    private void updateSuccessStatusInfoByAsync(Integer infoIdx) {
        IfInfoVO info = IfInfoVO.builder()
                .idx(infoIdx)
                .xstat(InterfaceResponseStatus.S)
                .xmsg(InterfaceResponseStatus.S.getMessage())
                .build();
        infoService.updateStatusInfo(info);
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

    private TrsResult createTrsSuccessResult(Integer infoIdx, int degree) {
        return TrsResult.builder()
                .ifInfoIdx(infoIdx)
                .degree(degree)
                .trsStatus(InterfaceTrsStatus.SUCCESS)
                .responseStatus(InterfaceResponseStatus.S)
                .trsDate(LocalDateTime.now())
                .build();
    }

    private TrsResult createTrsErrorResult(Integer infoIdx, int degree) {
        return TrsResult.builder()
                .ifInfoIdx(infoIdx)
                .degree(degree)
                .trsStatus(InterfaceTrsStatus.FAILED)
                .responseStatus(InterfaceResponseStatus.E)
                .trsDate(LocalDateTime.now())
                .build();
    }

}