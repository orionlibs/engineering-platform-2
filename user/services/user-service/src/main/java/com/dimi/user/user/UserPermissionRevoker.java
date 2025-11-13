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
class UserPermissionRevoker
{
    @Autowired private UserPermissionsPerAuthorityService userPermissionsPerAuthorityService;
    @Autowired private UsersDAO dao;


    @Transactional
    RevokePermissionForUserResult revokePermission(UUID userID, UUID permissionID)
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
                Optional<UserPermissionsPerAuthorityModel> permissionWrap = userPermissionsPerAuthorityService.getByID(permissionID);
                if(permissionWrap.isPresent())
                {
                    UserPermissionsPerAuthorityModel permissionModel = permissionWrap.get();
                    user.getPermissions().remove(permissionModel);
                    return RevokePermissionForUserResult.builder()
                                    .user(dao.save(user))
                                    .build();
                }
                else
                {
                    AError error = new AError<>();
                    error.setErrorCode(UserError.USER_PERMISSION_NOT_FOUND);
                    return RevokePermissionForUserResult.builder()
                                    .error(error)
                                    .build();
                }
            }
            else
            {
                AError error = new AError<>();
                error.setErrorCode(UserError.USER_PERMISSION_NOT_FOUND);
                return RevokePermissionForUserResult.builder()
                                .error(error)
                                .build();
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(UserError.USER_NOT_FOUND);
            return RevokePermissionForUserResult.builder()
                            .error(error)
                            .build();
        }
    }
}
