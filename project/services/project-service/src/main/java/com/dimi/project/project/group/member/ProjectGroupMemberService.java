package com.dimi.project.project.group.member;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.project.model.project.ProjectModel;
import com.dimi.project.model.project.group.ProjectGroupMemberModel;
import com.dimi.project.model.project.group.ProjectGroupMembersDAO;
import com.dimi.project.model.project.group.ProjectGroupModel;
import com.dimi.project.project.ProjectService;
import com.dimi.project.project.group.ProjectGroupService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectGroupMemberService
{
    @Autowired private ProjectService projectService;
    @Autowired private ProjectGroupService projectGroupService;
    @Autowired private ProjectGroupMembersDAO dao;


    @Transactional
    public AddProjectToGroupResult addProjectToGroup(UUID projectID, UUID projectGroupID)
    {
        Optional<ProjectModel> project = projectService.getByID(projectID);
        Optional<ProjectGroupModel> projectGroup = projectGroupService.getByID(projectGroupID);
        if(project.isPresent() && projectGroup.isPresent())
        {
            ProjectGroupMemberModel projectGroupMember = new ProjectGroupMemberModel(project.get(), projectGroup.get());
            try
            {
                return AddProjectToGroupResult.builder()
                                .projectGroupMember(save(projectGroupMember))
                                .build();
            }
            catch(DuplicateRecordException e)
            {
                AError error = new AError<>();
                error.setErrorCode(ProjectGroupMemberError.PROJECT_IS_ALREADY_MEMBER_OF_GROUP);
                return AddProjectToGroupResult.builder()
                                .error(error)
                                .build();
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


    @Transactional
    public ProjectGroupMemberModel save(ProjectGroupMemberModel projectGroupMemberModel)
    {
        return dao.save(projectGroupMemberModel);
    }


    public List<ProjectGroupMemberModel> getProjectsByGroupID(UUID projectGroupID)
    {
        return dao.findByProjectGroupId(projectGroupID);
    }


    public Optional<ProjectGroupMemberModel> getByID(UUID projectGroupMemberID)
    {
        return dao.findById(projectGroupMemberID);
    }


    public void delete(UUID projectGroupMemberID)
    {
        dao.deleteById(projectGroupMemberID);
    }


    public void deleteAll()
    {
        dao.deleteAll();
    }
}
