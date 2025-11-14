package com.dimi.project.project.member;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.member.ProjectMemberModel;
import com.dimi.project.model.project.member.ProjectMembersDAO;
import com.dimi.project.project.ProjectService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserToProjectAdder
{
    @Autowired private ProjectService projectService;
    @Autowired private ProjectMembersDAO dao;


    @Transactional
    AddUserToProjectResult addUserToProject(UUID projectID, UUID userID)
    {
        Optional<ProjectModel> project = projectService.getByID(projectID);
        if(project.isPresent())
        {
            ProjectMemberModel projectMember = new ProjectMemberModel(userID, project.get());
            try
            {
                ProjectMemberModel saved = dao.save(projectMember);
                dao.flush();
                return AddUserToProjectResult.builder()
                                .projectMember(saved)
                                .build();
            }
            catch(DataIntegrityViolationException e)
            {
                throw new DuplicateRecordException(ProjectMemberError.USER_IS_ALREADY_MEMBER_OF_PROJECT);
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(ProjectMemberError.PROJECT_NOT_FOUND);
            return AddUserToProjectResult.builder()
                            .error(error)
                            .build();
        }
    }
}
