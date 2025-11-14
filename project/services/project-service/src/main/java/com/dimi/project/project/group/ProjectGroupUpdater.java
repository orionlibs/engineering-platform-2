package com.dimi.project.project.group;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.project.api.project.group.UpdateProjectGroupAPI.UpdateProjectGroupRequest;
import com.dimi.project.model.project.group.ProjectGroupModel;
import com.dimi.project.model.project.group.ProjectGroupsDAO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class ProjectGroupUpdater
{
    @Autowired private ProjectGroupsDAO dao;


    @Transactional
    UpdateProjectGroupResult updateProjectGroup(UUID projectGroupID, UpdateProjectGroupRequest projectGroupAttributes)
    {
        Optional<ProjectGroupModel> wrap = dao.findById(projectGroupID);
        if(wrap.isPresent())
        {
            ProjectGroupModel projectGroup = wrap.get();
            projectGroup.setName(projectGroupAttributes.getName());
            projectGroup.setDescription(projectGroupAttributes.getDescription());
            try
            {
                ProjectGroupModel saved = dao.save(projectGroup);
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
}
