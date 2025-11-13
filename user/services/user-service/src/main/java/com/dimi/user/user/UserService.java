package com.dimi.user.user;

import com.dimi.user.api.authority.AssignAuthorityToUserAPI.AssignAuthorityToUserRequest;
import com.dimi.user.api.authority.UnassignAuthorityToUserAPI.UnassignAuthorityToUserRequest;
import com.dimi.user.api.user.CreateUserAPI.NewUserRequest;
import com.dimi.user.api.user.UpdateUserAPI.UpdateUserRequest;
import com.dimi.user.model.UserModel;
import com.dimi.user.model.UsersDAO;
import com.dimi.user.user.authority.AssignAuthorityToUserResult;
import com.dimi.user.user.authority.UnassignAuthorityToUserResult;
import com.dimi.user.user.authority.UserAuthorityAssigner;
import com.dimi.user.user.authority.UserAuthorityUnassigner;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService
{
    @Autowired private UserCreator userCreator;
    @Autowired private UserUpdater userUpdater;
    @Autowired private UserAuthorityAssigner userAuthorityAssigner;
    @Autowired private UserAuthorityUnassigner userAuthorityUnassigner;
    @Autowired private UserPermissionGranter userPermissionGranter;
    @Autowired private UserPermissionRevoker userPermissionRevoker;
    @Autowired private UsersDAO dao;


    @Transactional
    public CreateUserResult createUser(NewUserRequest request)
    {
        return userCreator.createUser(request);
    }


    @Transactional
    public UpdateUserResult updateUser(UUID userID, UpdateUserRequest request)
    {
        return userUpdater.updateUser(userID, request);
    }


    @Transactional
    public AssignAuthorityToUserResult assignAuthority(AssignAuthorityToUserRequest request)
    {
        return userAuthorityAssigner.assignAuthority(request);
    }


    @Transactional
    public UnassignAuthorityToUserResult unassignAuthority(UnassignAuthorityToUserRequest request)
    {
        return userAuthorityUnassigner.unassignAuthority(request);
    }


    @Transactional
    public GrantPermissionToUserResult grantPermission(UUID userID, UUID permissionID)
    {
        return userPermissionGranter.grantPermission(userID, permissionID);
    }


    @Transactional
    public RevokePermissionForUserResult revokePermission(UUID userID, UUID permissionID)
    {
        return userPermissionRevoker.revokePermission(userID, permissionID);
    }


    @Transactional
    public UserModel save(UserModel user)
    {
        return dao.save(user);
    }


    public Optional<UserModel> getByID(UUID userID)
    {
        return dao.findById(userID);
    }


    public void delete(UUID userID)
    {
        dao.deleteById(userID);
    }


    public void deleteAll()
    {
        dao.deleteAll();
    }
}
