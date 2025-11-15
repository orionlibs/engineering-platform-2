package com.dimi.project.user_group;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.project.model.user_group.UserGroupModel;
import com.dimi.project.model.user_group.UserGroupsDAO;
import com.dimi.project.user_group.request.CreateUserGroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserGroupCreator
{
    @Autowired private UserGroupsDAO dao;


    @Transactional
    CreateUserGroupResult createGroup(CreateUserGroupRequest request)
    {
        UserGroupModel userGroup = new UserGroupModel(request.getName(), request.getDescription());
        try
        {
            UserGroupModel saved = dao.save(userGroup);
            dao.flush();
            return CreateUserGroupResult.builder()
                            .userGroup(saved)
                            .build();
        }
        catch(DataIntegrityViolationException e)
        {
            throw new DuplicateRecordException(UserGroupError.USER_GROUP_ALREADY_EXISTS);
        }
    }
}
