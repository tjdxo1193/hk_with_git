package lims.api.kp.service.impl;

import lims.api.kp.dao.AddSampleLabelPubDao;
import lims.api.kp.enums.AddSampleProgress;
import lims.api.kp.service.AddSampleLabelPubService;
import lims.api.kp.vo.AddSampleLabelPubVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddSampleLabelPubServiceImpl implements AddSampleLabelPubService {
    private final AddSampleLabelPubDao dao;

    @Override
    public List<AddSampleLabelPubVO> find(AddSampleLabelPubVO param) {
        param.setAddSmpProc(AddSampleProgress.APPROVE_ADD_SAMPLE.getCode());
        return dao.find(param);
    }
}
