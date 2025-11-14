package com.dimi.project.user_group;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.project.api.user_group.AddUserToUserGroupAPI.AddUserToUserGroupRequest;
import com.dimi.project.model.user_group.UserGroupModel;
import com.dimi.project.model.user_group.UserGroupsDAO;
import com.dimi.project.model.user_group.UserInUserGroupModel;
import com.dimi.project.model.user_group.UsersInUserGroupsDAO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserToUserGroupAdder
{
    @Autowired private UserGroupsDAO userGroupsDAO;
    @Autowired private UsersInUserGroupsDAO usersInUserGroupsDAO;


    @Transactional
    AddUserToUserGroupResult addUserToUserGroup(UUID userGroupID, AddUserToUserGroupRequest request)
    {
        Optional<UserGroupModel> userGroupWrap = userGroupsDAO.findById(userGroupID);
        if(userGroupWrap.isPresent())
        {
            UserGroupModel userGroup = userGroupWrap.get();
            UserInUserGroupModel userInUserGroup = new UserInUserGroupModel(request.getUserID(), userGroup);
            try
            {
                UserInUserGroupModel saved = usersInUserGroupsDAO.save(userInUserGroup);
                usersInUserGroupsDAO.flush();
                return AddUserToUserGroupResult.builder()
                                .userInUserGroup(saved)
                                .build();
            }
            catch(DataIntegrityViolationException e)
            {
                throw new DuplicateRecordException(UserGroupError.USER_ALREADY_IN_USER_GROUP);
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(UserGroupError.USER_GROUP_NOT_FOUND);
            return AddUserToUserGroupResult.builder()
                            .error(error)
                            .build();
        }
    }
}
