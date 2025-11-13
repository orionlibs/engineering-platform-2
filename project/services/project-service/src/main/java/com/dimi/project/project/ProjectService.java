package com.dimi.project.project;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.project.api.project.CreateProjectAPI.NewProjectRequest;
import com.dimi.project.api.project.UpdateProjectAPI.UpdateProjectRequest;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.ProjectType;
import com.dimi.project.model.project.ProjectsDAO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService
{
    @Autowired private ProjectsDAO dao;


    @Transactional
    public CreateProjectResult createProject(NewProjectRequest request)
    {
        ProjectModel model = new ProjectModel(request.getName(), request.getCode(), request.getType(), request.getDescription(), request.getAvatarURL());
        try
        {
            ProjectModel saved = save(model);
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


    @Transactional
    public UpdateProjectResult updateProject(UUID projectID, UpdateProjectRequest request)
    {
        Optional<ProjectModel> wrap = getByID(projectID);
        if(wrap.isPresent())
        {
            ProjectModel project = wrap.get();
            project.setName(request.getName());
            project.setCode(request.getCode());
            project.setType(request.getType());
            project.setDescription(request.getDescription());
            try
            {
                ProjectModel saved = save(project);
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


    @Transactional
    public UpdateProjectAvatarResult updateAvatar(UUID projectID, String avatarURL)
    {
        Optional<ProjectModel> wrap = getByID(projectID);
        if(wrap.isPresent())
        {
            ProjectModel project = wrap.get();
            project.setAvatarURL(avatarURL);
            return UpdateProjectAvatarResult.builder()
                            .project(save(project))
                            .build();
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(ProjectError.PROJECT_NOT_FOUND);
            return UpdateProjectAvatarResult.builder()
                            .error(error)
                            .build();
        }
    }


    @Transactional
    public UpdateProjectManagerResult updateManager(UUID projectID, UUID managerUserID)
    {
        Optional<ProjectModel> wrap = getByID(projectID);
        if(wrap.isPresent())
        {
            ProjectModel project = wrap.get();
            project.setManagerUserID(managerUserID);
            return UpdateProjectManagerResult.builder()
                            .project(save(project))
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


    @Transactional
    public ProjectModel save(ProjectModel projectModel)
    {
        return dao.save(projectModel);
    }


    public Optional<ProjectModel> getByID(UUID projectID)
    {
        return dao.findById(projectID);
    }


    public List<ProjectModel> getByType(ProjectType.Type projectType)
    {
        return dao.findAllByType(projectType);
    }


    public void delete(UUID projectID)
    {
        dao.deleteById(projectID);
    }


    public void deleteAll()
    {
        dao.deleteAll();
    }
}
