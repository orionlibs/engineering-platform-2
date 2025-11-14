package com.dimi.project.permission;

import com.dimi.core.exception.AError;
import com.dimi.project.model.project.permission.PermissionAssignedToUserModel;
import com.dimi.project.model.project.permission.PermissionModel;
import com.dimi.project.model.project.permission.PermissionsAssignedToUsersDAO;
import com.dimi.project.model.project.permission.PermissionsDAO;
import com.dimi.project.model.user_group.UserGroupModel;
import com.dimi.project.model.user_group.UserGroupsDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserGroupPermissionAssigner
{
    @Autowired private PermissionsAssignedToUsersDAO permissionsAssignedToUsersDAO;
    @Autowired private UserGroupsDAO userGroupsDAO;
    @Autowired private PermissionsDAO permissionsDAO;


    @Transactional
    AssignPermissionToUserGroupResult assignPermissionToUserGroup(UUID permissionID, UUID userGroupID)
    {
        Optional<PermissionModel> permissionWrap = permissionsDAO.findById(permissionID);
        if(permissionWrap.isPresent())
        {
            PermissionModel permission = permissionWrap.get();
            Optional<UserGroupModel> userGroupWrap = userGroupsDAO.findById(userGroupID);
            if(userGroupWrap.isPresent())
            {
                List<UUID> userIDs = new ArrayList<>();
                userGroupWrap.get()
                                .getUsersAssignedToUserGroup()
                                .forEach(user -> userIDs.add(user.getUserID()));
                List<PermissionAssignedToUserModel> models = userIDs.stream()
                                .map(u -> new PermissionAssignedToUserModel(permission, u))
                                .toList();
                permissionsAssignedToUsersDAO.saveAll(models);
                permissionsAssignedToUsersDAO.flush();
            }
            return AssignPermissionToUserGroupResult.builder().build();
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(PermissionError.PERMISSION_NOT_FOUND);
            return AssignPermissionToUserGroupResult.builder()
                            .error(error)
                            .build();
        }
    }
}
