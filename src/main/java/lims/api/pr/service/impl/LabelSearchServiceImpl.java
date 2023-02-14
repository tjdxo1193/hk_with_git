package lims.api.pr.service.impl;

import lims.api.kp.enums.AddSampleProgress;
import lims.api.ms.enums.PItemType;
import lims.api.pr.dao.LabelSearchDao;
import lims.api.pr.enums.LabelPrtProcess;
import lims.api.pr.service.LabelSearchService;
import lims.api.pr.vo.LabelSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelSearchServiceImpl implements LabelSearchService {

    private final LabelSearchDao labelSearchDao;

    // 라벨출력조회 조회
    @Override
    public List<LabelSearchVO> findAll(LabelSearchVO param) {
        param.setPitmCdForDecode(PItemType.RAW_MATERIAL.getCode());
        param.setAddSmpProc(AddSampleProgress.APPROVE_ADD_SAMPLE.getCode());
        List<String> labelPrtProcList = Arrays.asList(LabelPrtProcess.LABEL_PRINT_PROCESSED.getCode(), LabelPrtProcess.LABEL_PRINT_CANCEL.getCode());
        param.setLabelPrtProcList(labelPrtProcList);
        return labelSearchDao.findAll(param);
    }
}
