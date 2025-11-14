package com.dimi.project.user_group;

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
    @Autowired private UserGroupCreator userGroupCreator;
    @Autowired private UserGroupUpdater userGroupUpdater;
    @Autowired private UserToUserGroupAdder userToUserGroupAdder;
    @Autowired private UserFromUserGroupRemover userFromUserGroupRemover;


    @Transactional
    public CreateUserGroupResult createGroup(NewUserGroupRequest request)
    {
        return userGroupCreator.createGroup(request);
    }


    @Transactional
    public UpdateUserGroupResult updateGroup(UUID userGroupID, UpdateUserGroupRequest request)
    {
        return userGroupUpdater.updateGroup(userGroupID, request);
    }


    @Transactional
    public AddUserToUserGroupResult addUserToUserGroup(UUID userGroupID, AddUserToUserGroupRequest request)
    {
        return userToUserGroupAdder.addUserToUserGroup(userGroupID, request);
    }


    @Transactional
    public RemoveUserFromUserGroupResult removeUserFromUserGroup(UUID userGroupID, UUID userID)
    {
        return userFromUserGroupRemover.removeUserFromUserGroup(userGroupID, userID);
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


    public Optional<UserInUserGroupModel> getUserInUserGroupByID(UUID userInUserGroupID)
    {
        return usersInUserGroupsDAO.findById(userInUserGroupID);
    }


    public Optional<UserInUserGroupModel> getUserInUserGroupByUserGroupIDAndUserID(UserGroupModel userGroup, UUID userID)
    {
        return usersInUserGroupsDAO.findByUserGroupAndUserID(userGroup, userID);
    }


    public List<UserGroupModel> getAll()
    {
        return dao.findAll();
    }


    public List<UserInUserGroupModel> getAllUserGroupsUserBelongsTo(UUID userID)
    {
        return usersInUserGroupsDAO.findAllByUserID(userID);
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
