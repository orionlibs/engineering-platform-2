package com.dimi.project.project.member;

import com.dimi.project.model.project.member.ProjectMemberModel;
import com.dimi.project.model.project.member.ProjectMembersDAO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectMemberService
{
    @Autowired private ProjectMembersDAO dao;
    @Autowired private UserToProjectAdder userToProjectAdder;
    @Autowired private UserFromProjectRemover userFromProjectRemover;


    @Transactional
    public AddUserToProjectResult addUserToProject(UUID projectID, UUID userID)
    {
        return userToProjectAdder.addUserToProject(projectID, userID);
    }


    @Transactional
    public RemoveUserFromProjectResult removeUserFromProject(UUID projectID, UUID userID)
    {
        return userFromProjectRemover.removeUserFromProject(projectID, userID);
    }


    @Transactional
    public ProjectMemberModel save(ProjectMemberModel model)
    {
        return dao.save(model);
    }


    public List<ProjectMemberModel> getProjectsThatUserIsMemberOf(UUID userID)
    {
        return dao.findAllByUserID(userID);
    }


    public Optional<ProjectMemberModel> getByID(UUID projectMemberID)
    {
        return dao.findById(projectMemberID);
    }


    public void delete(UUID projectMemberID)
    {
        dao.deleteById(projectMemberID);
    }


    public void deleteAll()
    {
        dao.deleteAll();
    }
}
