package lims.api.sy.service.impl;

import lims.api.common.dao.UserDao;
import lims.api.common.exception.MismatchedPasswordConditionException;
import lims.api.common.exception.NoUpdatedDataException;
import lims.api.sy.enums.PasswordConditionErrorCode;
import lims.api.sy.service.PwdChangeService;
import lims.api.sy.vo.PasswordConditionValidVO;
import lims.api.sy.vo.PwdChangeVO;
import lims.api.util.crypto.BcryptCrypto;
import lims.api.util.crypto.OneWayCrypto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PwdChangeServiceImpl implements PwdChangeService {
    private final UserDao dao;

    @Override
    public PwdChangeVO getUserInfo(PwdChangeVO param) {
        return dao.getUserInfoToPwdChange(param);
    }

    @Override
    public void pwdChange(PwdChangeVO param){
        assertValidPassword(param);

        OneWayCrypto crypto = new BcryptCrypto();
        param.setNewPwd(crypto.encrypt(param.getNewPwd()));
        int result = dao.pwdChange(param);

        if (result == 0){
            throw new NoUpdatedDataException();
        }
    }

    public void assertValidPassword(PwdChangeVO param) {
        PasswordConditionValidVO validVO = PasswordConditionValidVO.builder()
                .newPwd(param.getNewPwd())
                .oldPwd(dao.getPwd(param))
                .build();

        for (PasswordConditionErrorCode value : PasswordConditionErrorCode.values()) {
            if (value.isInvalid(validVO, param)) {
                throw new MismatchedPasswordConditionException(value.getCode());
            }
        }
    }

}