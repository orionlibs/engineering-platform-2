package com.dimi.project.permission;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.project.model.project.permission.PermissionModel;
import com.dimi.project.model.project.permission.PermissionsDAO;
import com.dimi.project.permission.request.CreateProjectPermissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class PermissionCreator
{
    @Autowired private PermissionsDAO dao;


    @Transactional
    CreatePermissionResult createPermission(CreateProjectPermissionRequest request)
    {
        PermissionModel model = new PermissionModel(request.getName(), request.getDescription());
        try
        {
            PermissionModel saved = dao.save(model);
            dao.flush();
            return CreatePermissionResult.builder()
                            .permission(saved)
                            .build();
        }
        catch(DataIntegrityViolationException e)
        {
            throw new DuplicateRecordException(PermissionError.PERMISSION_ALREADY_EXISTS);
        }
    }
}
