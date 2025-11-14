package com.dimi.project.project;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.project.api.project.UpdateProjectAPI.UpdateProjectRequest;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.ProjectsDAO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class ProjectUpdater
{
    @Autowired private ProjectsDAO dao;


    @Transactional
    UpdateProjectResult updateProject(UUID projectID, UpdateProjectRequest request)
    {
        Optional<ProjectModel> wrap = dao.findById(projectID);
        if(wrap.isPresent())
        {
            ProjectModel project = wrap.get();
            project.setName(request.getName());
            project.setCode(request.getCode());
            project.setType(request.getType());
            project.setDescription(request.getDescription());
            try
            {
                ProjectModel saved = dao.save(project);
                dao.flush();
                return UpdateProjectResult.builder()
                                .project(saved)
                                .build();
            }
            catch(DataIntegrityViolationException e)
            {
                throw new DuplicateRecordException(ProjectError.PROJECT_ALREADY_EXISTS);
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(ProjectError.PROJECT_NOT_FOUND);
            return UpdateProjectResult.builder()
                            .error(error)
                            .build();
        }
    }
}
