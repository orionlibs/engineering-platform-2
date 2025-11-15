package com.dimi.user.permission;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.user.model.user.permission.UserPermissionModel;
import com.dimi.user.model.user.permission.UserPermissionsDAO;
import com.dimi.user.permission.request.CreateUserPermissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserPermissionCreator
{
    @Autowired private UserPermissionsDAO dao;


    @Transactional
    CreatePermissionResult createPermission(CreateUserPermissionRequest request)
    {
        UserPermissionModel model = new UserPermissionModel(request.getName(), request.getDescription());
        try
        {
            UserPermissionModel saved = dao.save(model);
            dao.flush();
            return CreatePermissionResult.builder()
                            .permission(saved)
                            .build();
        }
        catch(DataIntegrityViolationException e)
        {
            throw new DuplicateRecordException(UserPermissionError.USER_PERMISSION_ALREADY_EXISTS);
        }
    }
}
