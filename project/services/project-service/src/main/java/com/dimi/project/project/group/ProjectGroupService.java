package com.dimi.project.project.group;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.project.api.project.group.CreateProjectGroupAPI.NewProjectGroupRequest;
import com.dimi.project.api.project.group.UpdateProjectGroupAPI.UpdateProjectGroupRequest;
import com.dimi.project.model.project.group.ProjectGroupModel;
import com.dimi.project.model.project.group.ProjectGroupsDAO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectGroupService
{
    @Autowired private ProjectGroupsDAO dao;


    public List<ProjectGroupModel> getAll()
    {
        return dao.findAll();
    }


    @Transactional
    public CreateProjectGroupResult createProjectGroup(NewProjectGroupRequest newProjectGroupAttributes)
    {
        ProjectGroupModel projectGroup = new ProjectGroupModel(newProjectGroupAttributes.getName(), newProjectGroupAttributes.getDescription());
        try
        {
            ProjectGroupModel saved = save(projectGroup);
            dao.flush();
            return CreateProjectGroupResult.builder()
                            .projectGroup(saved)
                            .build();
        }
        catch(DataIntegrityViolationException e)
        {
            throw new DuplicateRecordException(ProjectGroupError.PROJECT_GROUP_ALREADY_EXISTS);
        }
    }


    @Transactional
    public UpdateProjectGroupResult updateProjectGroup(UUID projectGroupID, UpdateProjectGroupRequest projectGroupAttributes)
    {
        Optional<ProjectGroupModel> wrap = getByID(projectGroupID);
        if(wrap.isPresent())
        {
            ProjectGroupModel projectGroup = wrap.get();
            projectGroup.setName(projectGroupAttributes.getName());
            projectGroup.setDescription(projectGroupAttributes.getDescription());
            try
            {
                ProjectGroupModel saved = save(projectGroup);
                dao.flush();
                return UpdateProjectGroupResult.builder()
                                .projectGroup(saved)
                                .build();
            }
            catch(DataIntegrityViolationException e)
            {
                throw new DuplicateRecordException(ProjectGroupError.PROJECT_GROUP_ALREADY_EXISTS);
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(ProjectGroupError.PROJECT_GROUP_NOT_FOUND);
            return UpdateProjectGroupResult.builder()
                            .error(error)
                            .build();
        }
    }


    @Transactional
    public ProjectGroupModel save(ProjectGroupModel projectGroupModel)
    {
        return dao.save(projectGroupModel);
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
