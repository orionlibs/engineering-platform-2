package com.dimi.project.permission;

import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.permission.PermissionAssociatedWithProjectModel;
import com.dimi.project.model.project.permission.PermissionModel;
import com.dimi.project.model.project.permission.PermissionsAssociatedWithProjectsDAO;
import com.dimi.project.model.project.permission.PermissionsDAO;
import com.dimi.project.project.ProjectService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class ProjectPermissionDisassociator
{
    @Autowired private PermissionsAssociatedWithProjectsDAO permissionsAssociatedWithProjectsDAO;
    @Autowired private PermissionsDAO dao;
    @Autowired private ProjectService projectService;


    @Transactional
    DisassociatePermissionFromProjectResult disassociatePermissionFromProject(UUID projectID, UUID permissionID)
    {
        Optional<PermissionModel> permissionWrap = dao.findById(permissionID);
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
}
