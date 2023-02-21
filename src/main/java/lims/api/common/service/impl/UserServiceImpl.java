package lims.api.common.service.impl;

import lims.api.common.dao.UserDao;
import lims.api.common.service.UserService;
import lims.api.common.vo.UserVO;
import lims.api.ms.enums.UserDelegate;
import lims.api.ms.enums.UserDelegateAppr;
import lims.api.ms.vo.UserDelegateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public boolean hasDelegateAssignUser(String userId) {
        UserDelegateVO param = UserDelegateVO.builder()
                .userId(userId)
                .delegateStatus(UserDelegate.DELEGATE)
                .delegateApprStatus(UserDelegateAppr.COMPLETE)
                .build();
        List<UserVO> delegateAssignUsers = userDao.findDelegateAssignUserIdsByUserId(param);
        return delegateAssignUsers.size() > 0;
    }

    /**
     * 자신을 위임자로 지정한 사용자들의 ids 배열을 가져오는 메서드입니다.
     * 자신을 위임자로 지정한 사용자가 존재할 때, 현재 위임을 받는 기간이라면
     * 위임한 사용자의 userId 목록을 콤마(,)로 구분한 문자열로 반환합니다.
     * 이 떄, 문자열에는 자신의 userId로 포함됩니다.
     *
     * @return 현재의 유저의 id와 현재 유저를 대리자로 지정한 유저들의 userId를 1, 2, 3... 변환한 형태의 문자열
     */
    @Override
    public String getDelegateAssignUserIdsWithMe(String userId) {
        UserDelegateVO param = UserDelegateVO.builder()
                .userId(userId)
                .delegateStatus(UserDelegate.DELEGATE)
                .delegateApprStatus(UserDelegateAppr.COMPLETE)
                .build();

        List<UserVO> result = new ArrayList<>();
        result.add(new UserVO(userId));

        List<UserVO> delegateAssignUsers = userDao.findDelegateAssignUserIdsByUserId(param);
        result.addAll(delegateAssignUsers);

        return result.stream().map(UserVO::getUserId).collect(Collectors.joining(","));
    }
}