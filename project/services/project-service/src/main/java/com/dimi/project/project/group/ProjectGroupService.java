package com.dimi.project.project.group;

import com.dimi.project.model.project.group.ProjectGroupModel;
import com.dimi.project.model.project.group.ProjectGroupsDAO;
import com.dimi.project.project.group.request.CreateProjectGroupRequest;
import com.dimi.project.project.group.request.UpdateProjectGroupRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectGroupService
{
    @Autowired private ProjectGroupsDAO dao;
    @Autowired private ProjectGroupCreator projectGroupCreator;
    @Autowired private ProjectGroupUpdater projectGroupUpdater;


    @Transactional
    public CreateProjectGroupResult createProjectGroup(CreateProjectGroupRequest newProjectGroupAttributes)
    {
        return projectGroupCreator.createProjectGroup(newProjectGroupAttributes);
    }


    @Transactional
    public UpdateProjectGroupResult updateProjectGroup(UUID projectGroupID, UpdateProjectGroupRequest projectGroupAttributes)
    {
        return projectGroupUpdater.updateProjectGroup(projectGroupID, projectGroupAttributes);
    }


    @Transactional
    public ProjectGroupModel save(ProjectGroupModel projectGroupModel)
    {
        return dao.save(projectGroupModel);
    }


    public List<ProjectGroupModel> getAll()
    {
        return dao.findAll();
    }


    public Optional<ProjectGroupModel> getByID(UUID projectGroupID)
    {
        return dao.findById(projectGroupID);
    }


    public void delete(UUID projectGroupID)
    {
        dao.deleteById(projectGroupID);
    }


    public void deleteAll()
    {
        dao.deleteAll();
    }
}
