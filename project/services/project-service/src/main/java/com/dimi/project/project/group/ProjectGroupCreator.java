package com.dimi.project.project.group;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.project.model.project.group.ProjectGroupModel;
import com.dimi.project.model.project.group.ProjectGroupsDAO;
import com.dimi.project.project.group.request.CreateProjectGroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class ProjectGroupCreator
{
    @Autowired private ProjectGroupsDAO dao;


    @Transactional
    CreateProjectGroupResult createProjectGroup(CreateProjectGroupRequest newProjectGroupAttributes)
    {
        ProjectGroupModel projectGroup = new ProjectGroupModel(newProjectGroupAttributes.getName(), newProjectGroupAttributes.getDescription());
        try
        {
            ProjectGroupModel saved = dao.save(projectGroup);
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
}
