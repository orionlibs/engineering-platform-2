package com.dimi.user.permission;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.user.api.permission.CreateUserPermissionForAuthorityAPI.NewUserPermissionForAuthorityRequest;
import com.dimi.user.authority.UserAuthorityService;
import com.dimi.user.model.authority.UserAuthorityModel;
import com.dimi.user.model.UserPermissionModel;
import com.dimi.user.model.UserPermissionsPerAuthorityDAO;
import com.dimi.user.model.UserPermissionsPerAuthorityModel;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserPermissionsForAuthorityCreator
{
    @Autowired private UserAuthorityService userAuthorityService;
    @Autowired private UserPermissionService userPermissionService;
    @Autowired private UserPermissionsPerAuthorityDAO dao;


    @Transactional
    CreatePermissionForAuthorityResult createPermissionForAuthority(NewUserPermissionForAuthorityRequest request)
    {
        Optional<UserAuthorityModel> authority = userAuthorityService.getByID(request.getAuthorityID());
        Optional<UserPermissionModel> permission = userPermissionService.getByID(request.getPermissionID());
        if(authority.isPresent() && permission.isPresent())
        {
            UserPermissionsPerAuthorityModel model = new UserPermissionsPerAuthorityModel(permission.get(), authority.get());
            try
            {
                UserPermissionsPerAuthorityModel saved = dao.save(model);
                dao.flush();
                return CreatePermissionForAuthorityResult.builder()
                                .permission(saved)
                                .build();
            }
            catch(DataIntegrityViolationException e)
            {
                throw new DuplicateRecordException(UserPermissionPerAuthorityError.USER_PERMISSION_ALREADY_EXISTS_FOR_AUTHORITY);
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
}
