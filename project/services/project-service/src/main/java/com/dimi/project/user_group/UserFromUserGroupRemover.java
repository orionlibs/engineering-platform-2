package com.dimi.project.user_group;

import com.dimi.core.exception.AError;
import com.dimi.project.model.user_group.UserGroupModel;
import com.dimi.project.model.user_group.UserGroupsDAO;
import com.dimi.project.model.user_group.UserInUserGroupModel;
import com.dimi.project.model.user_group.UsersInUserGroupsDAO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserFromUserGroupRemover
{
    @Autowired private UserGroupsDAO userGroupsDAO;
    @Autowired private UsersInUserGroupsDAO usersInUserGroupsDAO;


    @Transactional
    RemoveUserFromUserGroupResult removeUserFromUserGroup(UUID userGroupID, UUID userID)
    {
        Optional<UserGroupModel> userGroupWrap = userGroupsDAO.findById(userGroupID);
        if(userGroupWrap.isPresent())
        {
            UserGroupModel userGroup = userGroupWrap.get();
            Optional<UserInUserGroupModel> userInUserGroupWrap = getUserInUserGroupByUserGroupIDAndUserID(userGroup, userID);
            userInUserGroupWrap.ifPresent(userInUserGroupModel -> usersInUserGroupsDAO.delete(userInUserGroupModel));
            return RemoveUserFromUserGroupResult.builder().build();
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(UserGroupError.USER_GROUP_NOT_FOUND);
            return RemoveUserFromUserGroupResult.builder()
                            .error(error)
                            .build();
        }
    }


    private Optional<UserInUserGroupModel> getUserInUserGroupByUserGroupIDAndUserID(UserGroupModel userGroup, UUID userID)
    {
        return usersInUserGroupsDAO.findByUserGroupAndUserID(userGroup, userID);
    }
}
