package com.dimi.project.permission;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.project.model.project.permission.PermissionAssignedToUserModel;
import com.dimi.project.model.project.permission.PermissionModel;
import com.dimi.project.model.project.permission.PermissionsAssignedToUsersDAO;
import com.dimi.project.model.project.permission.PermissionsDAO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserPermissionAssigner
{
    @Autowired private PermissionsAssignedToUsersDAO permissionsAssignedToUsersDAO;
    @Autowired private PermissionsDAO dao;


    @Transactional
    AssignPermissionToUserResult assignPermissionToUser(UUID permissionID, UUID userID)
    {
        Optional<PermissionModel> permissionWrap = dao.findById(permissionID);
        if(permissionWrap.isPresent())
        {
            PermissionModel permission = permissionWrap.get();
            try
            {
                PermissionAssignedToUserModel model = new PermissionAssignedToUserModel(permission, userID);
                PermissionAssignedToUserModel saved = permissionsAssignedToUsersDAO.save(model);
                permissionsAssignedToUsersDAO.flush();
                return AssignPermissionToUserResult.builder()
                                .permission(saved)
                                .build();
            }
            catch(DataIntegrityViolationException e)
            {
                throw new DuplicateRecordException(PermissionError.PERMISSION_ALREADY_EXISTS);
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(PermissionError.PERMISSION_NOT_FOUND);
            return AssignPermissionToUserResult.builder()
                            .error(error)
                            .build();
        }
    }
}
