package com.dimi.organisation.api.user_assignments;

import com.dimi.core.api.APIResponse;
import com.dimi.organisation.user_assignments.AssignUserToOrganisationResult;
import com.dimi.organisation.user_assignments.UserAssignmentToOrganisationError;
import com.dimi.organisation.user_assignments.UserAssignmentToOrganisationService;
import com.dimi.organisation.user_assignments.request.AssignUserToOrganisationRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AssignUserToOrganisationAPI
{
    @Autowired private UserAssignmentToOrganisationService userAssignmentToOrganisationService;


    @PostMapping(value = "/organisations/{organisationID}/user-assignments")
    public ResponseEntity<APIResponse> assignUserToOrganisation(@PathVariable(name = "organisationID") UUID organisationID, @Valid @RequestBody AssignUserToOrganisationRequest request)
    {
        AssignUserToOrganisationResult result = userAssignmentToOrganisationService.assignUserToOrganisation(organisationID, request);
        if(result.getError() != null)
        {
            APIResponse response = APIResponse.ofError(result.getError());
            if(result.getError().getErrorCode().equals(UserAssignmentToOrganisationError.USER_ASSIGNMENT_TO_ORGANISATION_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.ok(new APIResponse());
    }
}
