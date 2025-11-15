package com.dimi.user.permission;

import com.dimi.core.exception.AError;
import com.dimi.user.model.user.UserModel;
import com.dimi.user.model.user.UsersDAO;
import com.dimi.user.model.user.permission.UserPermissionsPerAuthorityDAO;
import com.dimi.user.model.user.permission.UserPermissionsPerAuthorityModel;
import com.dimi.user.user.RevokePermissionForUserResult;
import com.dimi.user.user.UserError;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserPermissionRevoker
{
    @Autowired private UsersDAO usersDAO;
    @Autowired private UserPermissionsPerAuthorityDAO userPermissionsPerAuthorityDAO;


    @Transactional
    RevokePermissionForUserResult revokePermission(UUID userID, UUID permissionID)
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
                Optional<UserPermissionsPerAuthorityModel> permissionWrap = userPermissionsPerAuthorityDAO.findById(permissionID);
                if(permissionWrap.isPresent())
                {
                    UserPermissionsPerAuthorityModel permissionModel = permissionWrap.get();
                    user.getPermissions().remove(permissionModel);
                    return RevokePermissionForUserResult.builder()
                                    .user(usersDAO.save(user))
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
