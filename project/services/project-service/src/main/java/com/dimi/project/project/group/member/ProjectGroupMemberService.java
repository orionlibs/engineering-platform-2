package com.dimi.project.project.group.member;

import com.dimi.project.model.project.group.ProjectGroupMemberModel;
import com.dimi.project.model.project.group.ProjectGroupMembersDAO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectGroupMemberService
{
    @Autowired private ProjectGroupMembersDAO dao;
    @Autowired private ProjectToGroupAdder projectToGroupAdder;
    @Autowired private ProjectFromGroupRemover projectFromGroupRemover;


    @Transactional
    public AddProjectToGroupResult addProjectToGroup(UUID projectID, UUID projectGroupID)
    {
        return projectToGroupAdder.addProjectToGroup(projectID, projectGroupID);
    }


    @Transactional
    public RemoveProjectFromGroupResult removeProjectFromGroup(UUID projectID, UUID projectGroupID)
    {
        return projectFromGroupRemover.removeProjectFromGroup(projectID, projectGroupID);
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
