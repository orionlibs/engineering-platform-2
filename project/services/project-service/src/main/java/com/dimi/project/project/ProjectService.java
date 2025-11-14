package com.dimi.project.project;

import com.dimi.project.api.project.CreateProjectAPI.NewProjectRequest;
import com.dimi.project.api.project.UpdateProjectAPI.UpdateProjectRequest;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.ProjectType;
import com.dimi.project.model.project.ProjectsDAO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService
{
    @Autowired private ProjectsDAO dao;
    @Autowired private ProjectCreator projectCreator;
    @Autowired private ProjectUpdater projectUpdater;
    @Autowired private ProjectAvatarUpdater projectAvatarUpdater;
    @Autowired private ProjectManagerUpdater projectManagerUpdater;


    @Transactional
    public CreateProjectResult createProject(NewProjectRequest request)
    {
        return projectCreator.createProject(request);
    }


    @Transactional
    public UpdateProjectResult updateProject(UUID projectID, UpdateProjectRequest request)
    {
        return projectUpdater.updateProject(projectID, request);
    }


    @Transactional
    public UpdateProjectAvatarResult updateAvatar(UUID projectID, String avatarURL)
    {
        return projectAvatarUpdater.updateAvatar(projectID, avatarURL);
    }


    @Transactional
    public UpdateProjectManagerResult updateManager(UUID projectID, UUID managerUserID)
    {
        return projectManagerUpdater.updateManager(projectID, managerUserID);
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
