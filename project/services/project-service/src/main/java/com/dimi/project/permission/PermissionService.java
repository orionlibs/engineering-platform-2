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
import com.dimi.project.model.user_group.UserInUserGroupModel;
import com.dimi.project.project.ProjectService;
import com.dimi.project.user_group.UserGroupService;
import java.util.ArrayList;
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
    @Autowired private PermissionsDAO permissionsDAO;
    @Autowired private UserGroupService userGroupService;
    @Autowired private ProjectService projectService;
    @Autowired private PermissionCreator permissionCreator;
    @Autowired private UserPermissionAssigner permissionToUserAssigner;
    @Autowired private UserGroupPermissionAssigner permissionToUserGroupAssigner;
    @Autowired private UserPermissionUnassigner userPermissionUnassigner;
    @Autowired private UserGroupPermissionUnassigner userGroupPermissionUnassigner;
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
    public AssignPermissionToUserGroupResult assignPermissionToUserGroup(UUID permissionID, UUID userGroupID)
    {
        return permissionToUserGroupAssigner.assignPermissionToUserGroup(permissionID, userGroupID);
    }


    @Transactional
    public UnassignPermissionFromUserResult unassignPermissionFromUser(UUID permissionID, UUID userID)
    {
        return userPermissionUnassigner.unassignPermissionFromUser(permissionID, userID);
    }


    @Transactional
    public UnassignPermissionToUserGroupResult unassignPermissionFromUserGroup(UUID permissionID, UUID userGroupID)
    {
        return userGroupPermissionUnassigner.unassignPermissionFromUserGroup(permissionID, userGroupID);
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


    public List<PermissionModel> getAllPermissionsAssignedToUserGroup(UUID userGroupID)
    {
        List<UserInUserGroupModel> users = userGroupService.getAllUsersInUserGroup(userGroupID);
        List<UUID> userIDs = new ArrayList<>();
        users.forEach(user -> userIDs.add(user.getUserID()));
        List<PermissionAssignedToUserModel> models = permissionsAssignedToUsersDAO.findAllByUserIDIn(userIDs);
        return models.stream()
                        .map(m -> getByID(m.getPermission().getId()))
                        .filter(p -> p.isPresent())
                        .map(p -> p.get())
                        .toList();
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
        return permissionsDAO.save(model);
    }


    public Optional<PermissionModel> getByID(UUID permissionID)
    {
        return permissionsDAO.findById(permissionID);
    }


    public List<PermissionModel> getAll()
    {
        return permissionsDAO.findAll();
    }


    public void deleteAll()
    {
        permissionsDAO.deleteAll();
    }
}
