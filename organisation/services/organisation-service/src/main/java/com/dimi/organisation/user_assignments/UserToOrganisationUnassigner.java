package com.dimi.organisation.user_assignments;

import com.dimi.core.exception.AError;
import com.dimi.organisation.model.OrganisationModel;
import com.dimi.organisation.model.OrganisationsDAO;
import com.dimi.organisation.model.UserAssignmentToOrganisationModel;
import com.dimi.organisation.model.UserAssignmentsToOrganisationsDAO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserToOrganisationUnassigner
{
    @Autowired private OrganisationsDAO organisationsDAO;
    @Autowired private UserAssignmentsToOrganisationsDAO userAssignmentsToOrganisationsDAO;


    @Transactional
    UnassignUserFromOrganisationResult unassignUserFromOrganisation(UUID organisationID, UUID userID)
    {
        Optional<OrganisationModel> organisationWrap = organisationsDAO.findById(organisationID);
        if(organisationWrap.isPresent())
        {
            OrganisationModel organisation = organisationWrap.get();
            Optional<UserAssignmentToOrganisationModel> model = userAssignmentsToOrganisationsDAO.findByOrganisationAndUserID(organisation, userID);
            if(model.isPresent())
            {
                userAssignmentsToOrganisationsDAO.delete(model.get());
                userAssignmentsToOrganisationsDAO.flush();
                return UnassignUserFromOrganisationResult.builder().build();
            }
            else
            {
                AError error = new AError<>();
                error.setErrorCode(UserAssignmentToOrganisationError.USER_ASSIGNMENT_TO_ORGANISATION_NOT_FOUND);
                return UnassignUserFromOrganisationResult.builder()
                                .error(error)
                                .build();
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(UserAssignmentToOrganisationError.ORGANISATION_NOT_FOUND);
            return UnassignUserFromOrganisationResult.builder()
                            .error(error)
                            .build();
        }
    }
}
