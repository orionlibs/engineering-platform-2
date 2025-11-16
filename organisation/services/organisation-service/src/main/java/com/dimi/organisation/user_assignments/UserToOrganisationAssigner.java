package com.dimi.organisation.user_assignments;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.organisation.model.OrganisationModel;
import com.dimi.organisation.model.OrganisationsDAO;
import com.dimi.organisation.model.UserAssignmentToOrganisationModel;
import com.dimi.organisation.model.UserAssignmentsToOrganisationsDAO;
import com.dimi.organisation.user_assignments.request.AssignUserToOrganisationRequest;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserToOrganisationAssigner
{
    @Autowired private OrganisationsDAO organisationsDAO;
    @Autowired private UserAssignmentsToOrganisationsDAO userAssignmentsToOrganisationsDAO;


    @Transactional
    AssignUserToOrganisationResult assignUserToOrganisation(UUID organisationID, AssignUserToOrganisationRequest request)
    {
        Optional<OrganisationModel> organisationWrap = organisationsDAO.findById(organisationID);
        if(organisationWrap.isPresent())
        {
            OrganisationModel organisation = organisationWrap.get();
            UserAssignmentToOrganisationModel model = new UserAssignmentToOrganisationModel(organisation, request.getUserID());
            try
            {
                UserAssignmentToOrganisationModel saved = userAssignmentsToOrganisationsDAO.save(model);
                userAssignmentsToOrganisationsDAO.flush();
                return AssignUserToOrganisationResult.builder()
                                .userAssignmentToOrganisation(saved)
                                .build();
            }
            catch(DataIntegrityViolationException e)
            {
                throw new DuplicateRecordException(UserAssignmentToOrganisationError.USER_ASSIGNMENT_TO_ORGANISATION_ALREADY_EXISTS);
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(UserAssignmentToOrganisationError.USER_ASSIGNMENT_TO_ORGANISATION_NOT_FOUND);
            return AssignUserToOrganisationResult.builder()
                            .error(error)
                            .build();
        }
    }
}
