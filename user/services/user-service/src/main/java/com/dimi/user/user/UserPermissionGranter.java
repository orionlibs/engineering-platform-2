package com.dimi.user.user;

import com.dimi.core.exception.AError;
import com.dimi.user.model.UserModel;
import com.dimi.user.model.UserPermissionsPerAuthorityModel;
import com.dimi.user.model.UsersDAO;
import com.dimi.user.permission.UserPermissionsPerAuthorityService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserPermissionGranter
{
    @Autowired private UserPermissionsPerAuthorityService userPermissionsPerAuthorityService;
    @Autowired private UsersDAO dao;


    @Transactional
    GrantPermissionToUserResult grantPermission(UUID userID, UUID permissionID)
    {
        Optional<UserModel> wrap = dao.findById(userID);
        if(wrap.isPresent())
        {
            UserModel user = wrap.get();
            boolean userAlreadyHasPermission = user.getPermissions()
                            .stream()
                            .anyMatch(p -> p.getId().equals(permissionID));
            if(userAlreadyHasPermission)
            {
                AError error = new AError<>();
                error.setErrorCode(UserError.USER_ALREADY_HAS_PERMISSION);
                return GrantPermissionToUserResult.builder()
                                .error(error)
                                .build();
            }
            else
            {
                Optional<UserPermissionsPerAuthorityModel> permissionWrap = userPermissionsPerAuthorityService.getByID(permissionID);
                if(permissionWrap.isPresent())
                {
                    UserPermissionsPerAuthorityModel permissionModel = permissionWrap.get();
                    boolean authorityFound = user.getAuthorities()
                                    .stream()
                                    .anyMatch(a -> a.getId().equals(permissionModel.getAuthority().getId()));
                    if(authorityFound)
                    {
                        user.getPermissions().add(permissionModel);
                        return GrantPermissionToUserResult.builder()
                                        .user(dao.save(user))
                                        .build();
                    }
                    else
                    {
                        AError error = new AError<>();
                        error.setErrorCode(UserError.USER_AUTHORITY_NOT_FOUND);
                        return GrantPermissionToUserResult.builder()
                                        .error(error)
                                        .build();
                    }
                }
                else
                {
                    AError error = new AError<>();
                    error.setErrorCode(UserError.USER_PERMISSION_NOT_FOUND);
                    return GrantPermissionToUserResult.builder()
                                    .error(error)
                                    .build();
                }
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(UserError.USER_NOT_FOUND);
            return GrantPermissionToUserResult.builder()
                            .error(error)
                            .build();
        }
    }
}
