package lims.api.common.service.impl;

import lims.api.auth.domain.Token;
import lims.api.auth.service.TokenHttpResolver;
import lims.api.auth.service.impl.HttpHelper;
import lims.api.auth.service.impl.JwtResolver;
import lims.api.common.dao.ApproveDao;
import lims.api.common.domain.ESignInfo;
import lims.api.common.exception.NoCreatedDataException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApproveServiceImpl implements ApproveService {

    private final ApproveDao dao;
    private final TokenHttpResolver tokenHttpResolver;
    private final ESignHelper eSignHelper;
    private final JwtResolver jwtResolver;

    @Override
    public void create(ApproveVO param) {
        int result = dao.create(param);

        if(result == 0){
            throw new NoCreatedDataException();
        }
    }

    @Override
    public void update(ApproveVO param) {
        int result = dao.update(param);

        if(result == 0){
            throw new NoUpdatedDataException();
        }
    }

    @Override
    public void approve(Integer aprIdx) {
        ApproveVO approveInfo = setApprovedVO(aprIdx);
        int result = dao.approve(approveInfo);

        if(result == 0){
            throw new NoUpdatedDataException();
        }
    }

    public Integer requestApprove(ApproveVO param) {
        ApproveVO approveInfo = setReqeustedApproveVO(param);
        if(param.getAprIdx() != null) {
            approveInfo.setAprIdx(param.getAprIdx());
            update(approveInfo);
        }else {
            create(approveInfo);
        }

        return approveInfo.getAprIdx();
    }

    private ApproveVO setReqeustedApproveVO(ApproveVO param) {
        Token token = tokenHttpResolver.getAccessToken();
        String jwt = token.getJwt();

        if (jwt == null) {
            log.error("[{}] throw error during verify token", ThreadUtil.getCurrentMethodName());
        }

        ESignInfo esign = eSignHelper.getESignInfo();
        String userIp = HttpHelper.getClientIp();

        param.setPlntCd(jwtResolver.getPlantCode(jwt));
        param.setAprReqUid(jwtResolver.getUserId(jwt));
        param.setAprReqIp(userIp);
        if(StringUtils.isEmpty(param.getAprReqRea())){
            param.setAprReqRea(esign.getReason());
        }

        return param;
    }

    private ApproveVO setApprovedVO(Integer aprIdx) {
        ApproveVO approveVO = new ApproveVO();

        Token token = tokenHttpResolver.getAccessToken();
        String jwt = token.getJwt();

        if (jwt == null) {
            log.error("[{}] throw error during verify token", ThreadUtil.getCurrentMethodName());
        }

        ESignInfo esign = eSignHelper.getESignInfo();
        String userIp = HttpHelper.getClientIp();

        approveVO.setPlntCd(jwtResolver.getPlantCode(jwt));
        approveVO.setAprIdx(aprIdx);
        approveVO.setAprIp(userIp);
        approveVO.setAprRea(esign.getReason());

        return approveVO;
    }
}
