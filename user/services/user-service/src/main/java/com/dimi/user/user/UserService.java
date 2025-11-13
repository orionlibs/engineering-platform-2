package com.dimi.user.user;

import com.dimi.user.api.user.CreateUserAPI.NewUserRequest;
import com.dimi.user.api.user.UpdateUserAPI.UpdateUserRequest;
import com.dimi.user.model.UserModel;
import com.dimi.user.model.UsersDAO;
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
