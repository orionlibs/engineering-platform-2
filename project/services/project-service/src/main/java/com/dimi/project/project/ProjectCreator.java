package com.dimi.project.project;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.ProjectsDAO;
import com.dimi.project.project.request.CreateProjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class ProjectCreator
{
    @Autowired private ProjectsDAO dao;


    @Transactional
    CreateProjectResult createProject(CreateProjectRequest request)
    {
        ProjectModel model = new ProjectModel(request.getName(), request.getCode(), request.getType(), request.getDescription(), request.getAvatarURL());
        try
        {
            ProjectModel saved = dao.save(model);
            dao.flush();
            return CreateProjectResult.builder()
                            .project(saved)
                            .build();
        }
        catch(DataIntegrityViolationException e)
        {
            throw new DuplicateRecordException(ProjectError.PROJECT_ALREADY_EXISTS);
        }
    }
}
