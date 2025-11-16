package com.dimi.organisation.api;

import com.dimi.core.api.APIResponse;
import com.dimi.organisation.OrganisationError;
import com.dimi.organisation.OrganisationService;
import com.dimi.organisation.UpdateOrganisationResult;
import com.dimi.organisation.request.UpdateOrganisationRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UpdateOrganisationAPI
{
    @Autowired private OrganisationService organisationService;


    @PutMapping(value = "/organisations/{organisationID}")
    public ResponseEntity<APIResponse> updateOrganisation(@PathVariable(name = "organisationID") UUID organisationID, @Valid @RequestBody UpdateOrganisationRequest request)
    {
        UpdateOrganisationResult result = organisationService.updateOrganisation(organisationID, request);
        if(result.getError() != null)
        {
            APIResponse response = APIResponse.ofError(result.getError());
            if(result.getError().getErrorCode().equals(OrganisationError.ORGANISATION_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.ok(new APIResponse());
    }
}
