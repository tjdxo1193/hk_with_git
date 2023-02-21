package lims.api.sy.service;
import lims.api.sy.vo.PwdChangeVO;

public interface PwdChangeService{
    PwdChangeVO getUserInfo(PwdChangeVO param);

    void pwdChange(PwdChangeVO param);
}
