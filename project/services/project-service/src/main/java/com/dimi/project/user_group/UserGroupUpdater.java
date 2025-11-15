package com.dimi.project.user_group;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.project.model.user_group.UserGroupModel;
import com.dimi.project.model.user_group.UserGroupsDAO;
import com.dimi.project.user_group.request.UpdateUserGroupRequest;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserGroupUpdater
{
    @Autowired private UserGroupsDAO dao;


    @Transactional
    UpdateUserGroupResult updateGroup(UUID userGroupID, UpdateUserGroupRequest request)
    {
        Optional<UserGroupModel> wrap = dao.findById(userGroupID);
        if(wrap.isPresent())
        {
            UserGroupModel userGroup = wrap.get();
            userGroup.setName(request.getName());
            userGroup.setDescription(request.getDescription());
            try
            {
                UserGroupModel saved = dao.save(userGroup);
                dao.flush();
                return UpdateUserGroupResult.builder()
                                .userGroup(saved)
                                .build();
            }
            catch(DataIntegrityViolationException e)
            {
                throw new DuplicateRecordException(UserGroupError.USER_GROUP_ALREADY_EXISTS);
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(UserGroupError.USER_GROUP_NOT_FOUND);
            return UpdateUserGroupResult.builder()
                            .error(error)
                            .build();
        }
    }
}
