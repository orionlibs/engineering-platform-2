package com.dimi.organisation.api.user_assignments;

import com.dimi.core.api.APIResponse;
import com.dimi.organisation.user_assignments.UnassignUserFromOrganisationResult;
import com.dimi.organisation.user_assignments.UserAssignmentToOrganisationError;
import com.dimi.organisation.user_assignments.UserAssignmentToOrganisationService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UnassignUserFromOrganisationAPI
{
    @Autowired private UserAssignmentToOrganisationService userAssignmentToOrganisationService;


    @DeleteMapping(value = "/organisations/{organisationID}/user-assignments/{userID}")
    public ResponseEntity<APIResponse> unassignUserFromOrganisation(@PathVariable(name = "organisationID") UUID organisationID, @PathVariable(name = "userID") UUID userID)
    {
        UnassignUserFromOrganisationResult result = userAssignmentToOrganisationService.unassignUserFromOrganisation(organisationID, userID);
        if(result.getError() != null)
        {
            APIResponse response = APIResponse.ofError(result.getError());
            if(result.getError().getErrorCode().equals(UserAssignmentToOrganisationError.ORGANISATION_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
            else if(result.getError().getErrorCode().equals(UserAssignmentToOrganisationError.USER_ASSIGNMENT_TO_ORGANISATION_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.ok(new APIResponse());
    }
}
