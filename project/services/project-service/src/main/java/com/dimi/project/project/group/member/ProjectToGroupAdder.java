package com.dimi.project.project.group.member;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.group.ProjectGroupMemberModel;
import com.dimi.project.model.project.group.ProjectGroupMembersDAO;
import com.dimi.project.model.project.group.ProjectGroupModel;
import com.dimi.project.project.ProjectService;
import com.dimi.project.project.group.ProjectGroupService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class ProjectToGroupAdder
{
    @Autowired private ProjectService projectService;
    @Autowired private ProjectGroupService projectGroupService;
    @Autowired private ProjectGroupMembersDAO dao;


    @Transactional
    AddProjectToGroupResult addProjectToGroup(UUID projectID, UUID projectGroupID)
    {
        Optional<ProjectModel> project = projectService.getByID(projectID);
        Optional<ProjectGroupModel> projectGroup = projectGroupService.getByID(projectGroupID);
        if(project.isPresent() && projectGroup.isPresent())
        {
            ProjectGroupMemberModel projectGroupMember = new ProjectGroupMemberModel(project.get(), projectGroup.get());
            try
            {
                ProjectGroupMemberModel saved = dao.save(projectGroupMember);
                dao.flush();
                return AddProjectToGroupResult.builder()
                                .projectGroupMember(saved)
                                .build();
            }
            catch(DataIntegrityViolationException e)
            {
                throw new DuplicateRecordException(ProjectGroupMemberError.PROJECT_IS_ALREADY_MEMBER_OF_GROUP);
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(ProjectGroupMemberError.PROJECT_OR_PROJECT_GROUP_NOT_FOUND);
            return AddProjectToGroupResult.builder()
                            .error(error)
                            .build();
        }
    }
}
