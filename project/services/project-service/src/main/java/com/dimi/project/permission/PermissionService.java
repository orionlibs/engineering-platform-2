package com.dimi.project.permission;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.project.api.project.permission.AssignProjectPermissionToUserAPI.AssignPermissionToUserRequest;
import com.dimi.project.api.project.permission.CreateProjectPermissionAPI.NewPermissionRequest;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.permission.PermissionAssignedToUserModel;
import com.dimi.project.model.project.permission.PermissionAssociatedWithProjectModel;
import com.dimi.project.model.project.permission.PermissionModel;
import com.dimi.project.model.project.permission.PermissionsAssignedToUsersDAO;
import com.dimi.project.model.project.permission.PermissionsAssociatedWithProjectsDAO;
import com.dimi.project.model.project.permission.PermissionsDAO;
import com.dimi.project.project.ProjectService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionService
{
    @Autowired private PermissionsAssignedToUsersDAO permissionsAssignedToUsersDAO;
    @Autowired private PermissionsAssociatedWithProjectsDAO permissionsAssociatedWithProjectsDAO;
    @Autowired private PermissionsDAO dao;
    @Autowired private ProjectService projectService;


    @Transactional
    public CreatePermissionResult createPermission(NewPermissionRequest request)
    {
        PermissionModel model = new PermissionModel(request.getName(), request.getDescription());
        try
        {
            return CreatePermissionResult.builder()
                            .permission(save(model))
                            .build();
        }
        catch(DuplicateRecordException e)
        {
            AError error = new AError<>();
            error.setErrorCode(PermissionError.PERMISSION_ALREADY_EXISTS);
            return CreatePermissionResult.builder()
                            .error(error)
                            .build();
        }
    }


    @Transactional
    public AssignPermissionToUserResult assignPermissionToUser(UUID permissionID, AssignPermissionToUserRequest request)
    {
        Optional<PermissionModel> permissionWrap = getByID(permissionID);
        if(permissionWrap.isPresent())
        {
            PermissionModel permission = permissionWrap.get();
            Optional<PermissionAssignedToUserModel> permissionAssignedToUserWrap = permissionsAssignedToUsersDAO.findByPermissionAndUserID(permission, request.getUserID());
            if(permissionAssignedToUserWrap.isPresent())
            {
                AError error = new AError<>();
                error.setErrorCode(PermissionError.PERMISSION_ALREADY_EXISTS);
                return AssignPermissionToUserResult.builder()
                                .error(error)
                                .build();//test
            }
            else
            {
                PermissionAssignedToUserModel model = new PermissionAssignedToUserModel(permission, request.getUserID());
                return AssignPermissionToUserResult.builder()
                                .permission(permissionsAssignedToUsersDAO.save(model))
                                .build();
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


    @Transactional
    public UnassignPermissionFromUserResult unassignPermissionFromUser(UUID permissionID, UUID userID)
    {
        Optional<PermissionModel> permissionWrap = getByID(permissionID);
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


    @Transactional
    public AssociatePermissionWithProjectResult associatePermissionWithProject(UUID projectID, UUID permissionID)
    {
        Optional<PermissionModel> permissionWrap = getByID(permissionID);
        if(permissionWrap.isPresent())
        {
            PermissionModel permission = permissionWrap.get();
            Optional<ProjectModel> projectWrap = projectService.getByID(projectID);
            if(projectWrap.isPresent())
            {
                ProjectModel project = projectWrap.get();
                Optional<PermissionAssociatedWithProjectModel> permissionAssociatedWithProjectWrap = permissionsAssociatedWithProjectsDAO.findByPermissionAndProject(permission, project);
                if(permissionAssociatedWithProjectWrap.isPresent())
                {
                    AError error = new AError<>();
                    error.setErrorCode(PermissionError.PERMISSION_ALREADY_EXISTS);
                    return AssociatePermissionWithProjectResult.builder()
                                    .error(error)
                                    .build();
                }
                else
                {
                    PermissionAssociatedWithProjectModel model = new PermissionAssociatedWithProjectModel(permission, project);
                    return AssociatePermissionWithProjectResult.builder()
                                    .permission(permissionsAssociatedWithProjectsDAO.save(model))
                                    .build();
                }
            }
            else
            {
                AError error = new AError<>();
                error.setErrorCode(PermissionError.PROJECT_NOT_FOUND);
                return AssociatePermissionWithProjectResult.builder()
                                .error(error)
                                .build();
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(PermissionError.PERMISSION_NOT_FOUND);
            return AssociatePermissionWithProjectResult.builder()
                            .error(error)
                            .build();
        }
    }


    @Transactional
    public DisassociatePermissionFromProjectResult disassociatePermissionFromProject(UUID projectID, UUID permissionID)
    {
        Optional<PermissionModel> permissionWrap = getByID(permissionID);
        if(permissionWrap.isPresent())
        {
            PermissionModel permission = permissionWrap.get();
            Optional<ProjectModel> projectWrap = projectService.getByID(projectID);
            if(projectWrap.isPresent())
            {
                ProjectModel project = projectWrap.get();
                Optional<PermissionAssociatedWithProjectModel> permissionAssociatedWithProjectWrap = permissionsAssociatedWithProjectsDAO.findByPermissionAndProject(permission, project);
                if(permissionAssociatedWithProjectWrap.isPresent())
                {
                    permissionsAssociatedWithProjectsDAO.delete(permissionAssociatedWithProjectWrap.get());
                    return DisassociatePermissionFromProjectResult.builder().build();
                }
            }
        }
        return DisassociatePermissionFromProjectResult.builder().build();
    }


    public List<PermissionAssignedToUserModel> getAllPermissionsAssignedToUser(UUID userID)
    {
        return permissionsAssignedToUsersDAO.findAllByUserID(userID);
    }


    public List<PermissionAssociatedWithProjectModel> getAllPermissionsAssociatedWithProject(UUID projectID)
    {
        Optional<ProjectModel> projectWrap = projectService.getByID(projectID);
        if(projectWrap.isPresent())
        {
            ProjectModel project = projectWrap.get();
            return permissionsAssociatedWithProjectsDAO.findAllByProject(project);
        }
        return Collections.emptyList();
    }


    @Transactional
    public PermissionModel save(PermissionModel model)
    {
        return dao.save(model);
    }


    public Optional<PermissionModel> getByID(UUID permissionID)
    {
        return dao.findById(permissionID);
    }


    public List<PermissionModel> getAll()
    {
        return dao.findAll();
    }


    public void deleteAll()
    {
        dao.deleteAll();
    }
}
