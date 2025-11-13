package com.dimi.project.permission;

import com.dimi.project.model.project.permission.PermissionAssignedToUserModel;
import com.dimi.project.model.project.permission.PermissionModel;
import com.dimi.project.model.project.permission.PermissionsAssignedToUsersDAO;
import com.dimi.project.model.project.permission.PermissionsDAO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserPermissionUnassigner
{
    @Autowired private PermissionsAssignedToUsersDAO permissionsAssignedToUsersDAO;
    @Autowired private PermissionsDAO permissionsDAO;


    @Transactional
    UnassignPermissionFromUserResult unassignPermissionFromUser(UUID permissionID, UUID userID)
    {
        Optional<PermissionModel> permissionWrap = permissionsDAO.findById(permissionID);
        if(permissionWrap.isPresent())
        {
            PermissionModel permission = permissionWrap.get();
            Optional<PermissionAssignedToUserModel> permissionAssignedToUserWrap = permissionsAssignedToUsersDAO.findByPermissionAndUserID(permission, userID);
            if(permissionAssignedToUserWrap.isPresent())
            {
                permissionsAssignedToUsersDAO.delete(permissionAssignedToUserWrap.get());
                return UnassignPermissionFromUserResult.builder().build();
            }
        }
        return UnassignPermissionFromUserResult.builder().build();
    }
}
