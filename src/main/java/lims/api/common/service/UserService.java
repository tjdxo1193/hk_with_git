package lims.api.common.service;

public interface UserService {

    boolean hasDelegateAssignUser(String userId);

    String getDelegateAssignUserIdsWithMe(String userId);

}