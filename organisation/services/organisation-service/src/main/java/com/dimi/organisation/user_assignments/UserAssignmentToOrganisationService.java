package com.dimi.organisation.user_assignments;

import com.dimi.organisation.model.UserAssignmentToOrganisationModel;
import com.dimi.organisation.model.UserAssignmentsToOrganisationsDAO;
import com.dimi.organisation.user_assignments.request.AssignUserToOrganisationRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAssignmentToOrganisationService
{
    @Autowired private UserToOrganisationAssigner userToOrganisationAssigner;
    @Autowired private UserToOrganisationUnassigner userToOrganisationUnassigner;
    @Autowired private UserAssignmentsToOrganisationsDAO dao;


    @Transactional
    public AssignUserToOrganisationResult assignUserToOrganisation(UUID organisationID, AssignUserToOrganisationRequest request)
    {
        return userToOrganisationAssigner.assignUserToOrganisation(organisationID, request);
    }


    @Transactional
    public UnassignUserFromOrganisationResult unassignUserFromOrganisation(UUID organisationID, UUID userID)
    {
        return userToOrganisationUnassigner.unassignUserFromOrganisation(organisationID, userID);
    }


    @Transactional
    public UserAssignmentToOrganisationModel save(UserAssignmentToOrganisationModel user)
    {
        return dao.save(user);
    }


    public Optional<UserAssignmentToOrganisationModel> getByID(UUID id)
    {
        return dao.findById(id);
    }


    public List<UserAssignmentToOrganisationModel> getAll()
    {
        return dao.findAll();
    }


    public List<UserAssignmentToOrganisationModel> getAllByUserID(UUID userID)
    {
        return dao.findAllByUserID(userID);
    }


    public void delete(UUID id)
    {
        dao.deleteById(id);
    }


    public void deleteAll()
    {
        dao.deleteAll();
    }
}
