package com.dimi.user.permission;

import com.dimi.core.exception.AError;
import com.dimi.user.model.UserModel;
import com.dimi.user.model.UserPermissionsPerAuthorityDAO;
import com.dimi.user.model.UserPermissionsPerAuthorityModel;
import com.dimi.user.model.UsersDAO;
import com.dimi.user.user.GrantPermissionToUserResult;
import com.dimi.user.user.UserError;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserPermissionGranter
{
    @Autowired private UsersDAO usersDAO;
    @Autowired private UserPermissionsPerAuthorityDAO userPermissionsPerAuthorityDAO;


    @Transactional
    GrantPermissionToUserResult grantPermission(UUID userID, UUID permissionID)
    {
        Optional<UserModel> wrap = usersDAO.findById(userID);
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
                Optional<UserPermissionsPerAuthorityModel> permissionWrap = userPermissionsPerAuthorityDAO.findById(permissionID);
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
                                        .user(usersDAO.save(user))
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
