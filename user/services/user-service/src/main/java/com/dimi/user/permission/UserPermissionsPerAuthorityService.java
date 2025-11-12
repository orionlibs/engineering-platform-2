package com.dimi.user.permission;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.user.api.permission.CreateUserPermissionForAuthorityAPI.NewUserPermissionForAuthorityRequest;
import com.dimi.user.authority.UserAuthorityService;
import com.dimi.user.model.UserAuthorityModel;
import com.dimi.user.model.UserPermissionModel;
import com.dimi.user.model.UserPermissionsPerAuthorityDAO;
import com.dimi.user.model.UserPermissionsPerAuthorityModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPermissionsPerAuthorityService
{
    @Autowired private UserAuthorityService userAuthorityService;
    @Autowired private UserPermissionService userPermissionService;
    @Autowired private UserPermissionsPerAuthorityDAO dao;


    @Transactional
    public CreatePermissionForAuthorityResult createPermissionForAuthority(NewUserPermissionForAuthorityRequest request)
    {
        Optional<UserAuthorityModel> authority = userAuthorityService.getByID(request.getAuthorityID());
        Optional<UserPermissionModel> permission = userPermissionService.getByID(request.getPermissionID());
        if(authority.isPresent() && permission.isPresent())
        {
            UserPermissionsPerAuthorityModel model = new UserPermissionsPerAuthorityModel(permission.get(), authority.get());
            try
            {
                return CreatePermissionForAuthorityResult.builder()
                                .permission(save(model))
                                .build();
            }
            catch(DuplicateRecordException e)
            {
                AError error = new AError<>();
                error.setErrorCode(UserPermissionPerAuthorityError.USER_PERMISSION_ALREADY_EXISTS_FOR_AUTHORITY);
                return CreatePermissionForAuthorityResult.builder()
                                .error(error)
                                .build();
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(UserPermissionPerAuthorityError.USER_PERMISSION_OR_AUTHORITY_NOT_FOUND);
            return CreatePermissionForAuthorityResult.builder()
                            .error(error)
                            .build();
        }
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
