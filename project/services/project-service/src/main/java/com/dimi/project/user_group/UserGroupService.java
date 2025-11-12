package com.dimi.project.user_group;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.project.api.user_group.AddUserToUserGroupAPI.AddUserToUserGroupRequest;
import com.dimi.project.api.user_group.CreateUserGroupAPI.NewUserGroupRequest;
import com.dimi.project.api.user_group.UpdateUserGroupAPI.UpdateUserGroupRequest;
import com.dimi.project.model.user_group.UserGroupModel;
import com.dimi.project.model.user_group.UserGroupsDAO;
import com.dimi.project.model.user_group.UserInUserGroupModel;
import com.dimi.project.model.user_group.UsersInUserGroupsDAO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserGroupService
{
    @Autowired private UserGroupsDAO dao;
    @Autowired private UsersInUserGroupsDAO usersInUserGroupsDAO;


    @Transactional
    public CreateUserGroupResult createGroup(NewUserGroupRequest request)
    {
        UserGroupModel userGroup = new UserGroupModel(request.getName(), request.getDescription());
        try
        {
            return CreateUserGroupResult.builder()
                            .userGroup(save(userGroup))
                            .build();
        }
        catch(DuplicateRecordException e)
        {
            AError error = new AError<>();
            error.setErrorCode(UserGroupError.USER_GROUP_ALREADY_EXISTS);
            return CreateUserGroupResult.builder()
                            .error(error)
                            .build();
        }
    }


    @Transactional
    public UpdateUserGroupResult updateGroup(UUID userGroupID, UpdateUserGroupRequest request)
    {
        Optional<UserGroupModel> wrap = getByID(userGroupID);
        if(wrap.isPresent())
        {
            UserGroupModel userGroup = wrap.get();
            userGroup.setName(request.getName());
            userGroup.setDescription(request.getDescription());
            try
            {
                return UpdateUserGroupResult.builder()
                                .userGroup(save(userGroup))
                                .build();
            }
            catch(DuplicateRecordException e)
            {
                AError error = new AError<>();
                error.setErrorCode(UserGroupError.USER_GROUP_ALREADY_EXISTS);
                return UpdateUserGroupResult.builder()
                                .error(error)
                                .build();
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


    @Transactional
    public AddUserToUserGroupResult addUserToUserGroup(UUID userGroupID, AddUserToUserGroupRequest request)
    {
        Optional<UserGroupModel> userGroupWrap = getByID(userGroupID);
        if(userGroupWrap.isPresent())
        {
            UserGroupModel userGroup = userGroupWrap.get();
            UserInUserGroupModel userInUserGroup = new UserInUserGroupModel(request.getUserID(), userGroup);
            try
            {
                return AddUserToUserGroupResult.builder()
                                .userInUserGroup(usersInUserGroupsDAO.save(userInUserGroup))
                                .build();
            }
            catch(DuplicateRecordException e)
            {
                AError error = new AError<>();
                error.setErrorCode(UserGroupError.USER_ALREADY_IN_USER_GROUP);
                return AddUserToUserGroupResult.builder()
                                .error(error)
                                .build();
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


    @Transactional
    public UserGroupModel save(UserGroupModel model)
    {
        return dao.save(model);
    }


    public Optional<UserGroupModel> getByID(UUID userGroupID)
    {
        return dao.findById(userGroupID);
    }


    public List<UserGroupModel> getAll()
    {
        return dao.findAll();
    }


    public void delete(UUID userGroupID)
    {
        dao.deleteById(userGroupID);
    }


    public void deleteAll()
    {
        dao.deleteAll();
    }
}
