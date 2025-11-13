package com.dimi.user.permission;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.user.api.permission.CreateUserPermissionAPI.NewUserPermissionRequest;
import com.dimi.user.model.UserPermissionModel;
import com.dimi.user.model.UserPermissionsDAO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPermissionService
{
    @Autowired private UserPermissionsDAO dao;


    @Transactional
    public CreatePermissionResult createPermission(NewUserPermissionRequest request)
    {
        UserPermissionModel model = new UserPermissionModel(request.getName(), request.getDescription());
        try
        {
            UserPermissionModel saved = save(model);
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


    @Transactional
    public UserPermissionModel save(UserPermissionModel model)
    {
        return dao.save(model);
    }


    public Optional<UserPermissionModel> getByID(UUID userID)
    {
        return dao.findById(userID);
    }


    public List<UserPermissionModel> getAll()
    {
        return dao.findAll();
    }


    public void deleteAll()
    {
        dao.deleteAll();
    }
}
