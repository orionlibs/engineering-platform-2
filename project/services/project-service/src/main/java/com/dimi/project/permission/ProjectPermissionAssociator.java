package com.dimi.project.permission;

import com.dimi.core.exception.AError;
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
class ProjectPermissionAssociator
{
    @Autowired private PermissionsAssociatedWithProjectsDAO permissionsAssociatedWithProjectsDAO;
    @Autowired private PermissionsDAO dao;
    @Autowired private ProjectService projectService;


    @Transactional
    AssociatePermissionWithProjectResult associatePermissionWithProject(UUID projectID, UUID permissionID)
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
}
