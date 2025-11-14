package com.dimi.user.permission;

import com.dimi.user.api.permission.CreateUserPermissionAPI.NewUserPermissionRequest;
import com.dimi.user.model.user.permission.UserPermissionModel;
import com.dimi.user.model.user.permission.UserPermissionsDAO;
import com.dimi.user.user.GrantPermissionToUserResult;
import com.dimi.user.user.RevokePermissionForUserResult;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPermissionService
{
    @Autowired private UserPermissionCreator userPermissionCreator;
    @Autowired private UserPermissionGranter userPermissionGranter;
    @Autowired private UserPermissionRevoker userPermissionRevoker;
    @Autowired private UserPermissionsDAO dao;


    @Transactional
    public CreatePermissionResult createPermission(NewUserPermissionRequest request)
    {
        return userPermissionCreator.createPermission(request);
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
    public UserPermissionModel save(UserPermissionModel model)
    {
        return dao.save(model);
    }


    public Optional<UserPermissionModel> getByID(UUID userID)
    {
        return dao.findById(userID);
    }


    public List<UserPermissionModel> getAll()
    {
        return dao.findAll();
    }


    public void deleteAll()
    {
        dao.deleteAll();
    }
}
