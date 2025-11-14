package com.dimi.project.project.group.member;

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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class ProjectFromGroupRemover
{
    @Autowired private ProjectService projectService;
    @Autowired private ProjectGroupService projectGroupService;
    @Autowired private ProjectGroupMembersDAO dao;


    @Transactional
    RemoveProjectFromGroupResult removeProjectFromGroup(UUID projectID, UUID projectGroupID)
    {
        Optional<ProjectModel> project = projectService.getByID(projectID);
        Optional<ProjectGroupModel> projectGroup = projectGroupService.getByID(projectGroupID);
        if(project.isPresent() && projectGroup.isPresent())
        {
            Optional<ProjectGroupMemberModel> projectGroupMember = dao.findByProjectAndProjectGroup(project.get(), projectGroup.get());
            projectGroupMember.ifPresent(projectGroupMemberModel -> dao.delete(projectGroupMemberModel));
            return RemoveProjectFromGroupResult.builder().build();
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(ProjectGroupMemberError.PROJECT_OR_PROJECT_GROUP_NOT_FOUND);
            return RemoveProjectFromGroupResult.builder()
                            .error(error)
                            .build();
        }
    }
}
