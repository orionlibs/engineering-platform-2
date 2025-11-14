package com.dimi.user.permission;

import com.dimi.user.api.permission.CreateUserPermissionForAuthorityAPI.NewUserPermissionForAuthorityRequest;
import com.dimi.user.model.user.permission.UserPermissionsPerAuthorityDAO;
import com.dimi.user.model.user.permission.UserPermissionsPerAuthorityModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPermissionsPerAuthorityService
{
    @Autowired private UserPermissionsForAuthorityCreator userPermissionsForAuthorityCreator;
    @Autowired private UserPermissionsPerAuthorityDAO dao;


    @Transactional
    public CreatePermissionForAuthorityResult createPermissionForAuthority(NewUserPermissionForAuthorityRequest request)
    {
        return userPermissionsForAuthorityCreator.createPermissionForAuthority(request);
    }


    @Transactional
    public UserPermissionsPerAuthorityModel save(UserPermissionsPerAuthorityModel model)
    {
        return dao.save(model);
    }


    public List<UserPermissionsPerAuthorityModel> getAll()
    {
        return dao.findAll();
    }


    public Optional<UserPermissionsPerAuthorityModel> getByID(UUID id)
    {
        return dao.findById(id);
    }


    public Optional<UserPermissionsPerAuthorityModel> getByPermissionIDAndAuthorityID(UUID permissionID, UUID authorityID)
    {
        return dao.findByPermission_IdAndAuthority_Id(permissionID, authorityID);
    }


    public void deleteAll()
    {
        dao.deleteAll();
    }
}
