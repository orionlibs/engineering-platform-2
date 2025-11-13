package com.dimi.project.permission;

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
    @Autowired private PermissionCreator permissionCreator;
    @Autowired private UserPermissionAssigner permissionToUserAssigner;
    @Autowired private UserPermissionUnassigner userPermissionUnassigner;
    @Autowired private ProjectPermissionAssociator projectPermissionAssociator;
    @Autowired private ProjectPermissionDisassociator projectPermissionDisassociator;


    @Transactional
    public CreatePermissionResult createPermission(NewPermissionRequest request)
    {
        return permissionCreator.createPermission(request);
    }


    @Transactional
    public AssignPermissionToUserResult assignPermissionToUser(UUID permissionID, AssignPermissionToUserRequest request)
    {
        return permissionToUserAssigner.assignPermissionToUser(permissionID, request);
    }


    @Transactional
    public UnassignPermissionFromUserResult unassignPermissionFromUser(UUID permissionID, UUID userID)
    {
        return userPermissionUnassigner.unassignPermissionFromUser(permissionID, userID);
    }


    @Transactional
    public AssociatePermissionWithProjectResult associatePermissionWithProject(UUID projectID, UUID permissionID)
    {
        return projectPermissionAssociator.associatePermissionWithProject(projectID, permissionID);
    }


    @Transactional
    public DisassociatePermissionFromProjectResult disassociatePermissionFromProject(UUID projectID, UUID permissionID)
    {
        return projectPermissionDisassociator.disassociatePermissionFromProject(projectID, permissionID);
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
