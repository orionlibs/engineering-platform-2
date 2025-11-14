package com.dimi.project.project;

import com.dimi.core.exception.AError;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.ProjectsDAO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class ProjectManagerUpdater
{
    @Autowired private ProjectsDAO dao;


    @Transactional
    UpdateProjectManagerResult updateManager(UUID projectID, UUID managerUserID)
    {
        Optional<ProjectModel> wrap = dao.findById(projectID);
        if(wrap.isPresent())
        {
            ProjectModel project = wrap.get();
            project.setManagerUserID(managerUserID);
            return UpdateProjectManagerResult.builder()
                            .project(dao.save(project))
                            .build();
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(ProjectError.PROJECT_NOT_FOUND);
            return UpdateProjectManagerResult.builder()
                            .error(error)
                            .build();
        }
    }
}
