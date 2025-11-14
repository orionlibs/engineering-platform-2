package com.dimi.project.project.member;

import com.dimi.core.exception.AError;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.member.ProjectMemberModel;
import com.dimi.project.model.project.member.ProjectMembersDAO;
import com.dimi.project.project.ProjectService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserFromProjectRemover
{
    @Autowired private ProjectService projectService;
    @Autowired private ProjectMembersDAO dao;


    @Transactional
    RemoveUserFromProjectResult removeUserFromProject(UUID projectID, UUID userID)
    {
        Optional<ProjectModel> project = projectService.getByID(projectID);
        if(project.isPresent())
        {
            Optional<ProjectMemberModel> projectMemberWrap = dao.findByProjectAndUserID(project.get(), userID);
            if(projectMemberWrap.isPresent())
            {
                dao.delete(projectMemberWrap.get());
                dao.flush();
            }
            return RemoveUserFromProjectResult.builder().build();
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(ProjectMemberError.PROJECT_NOT_FOUND);
            return RemoveUserFromProjectResult.builder()
                            .error(error)
                            .build();
        }
    }
}
