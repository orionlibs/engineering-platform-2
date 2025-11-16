package com.dimi.organisation.api;

import com.dimi.core.api.APIResponse;
import com.dimi.organisation.CreateOrganisationResult;
import com.dimi.organisation.OrganisationService;
import com.dimi.organisation.request.CreateOrganisationRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CreateOrganisationAPI
{
    @Autowired private OrganisationService organisationService;


    @PostMapping(value = "/organisations")
    public ResponseEntity<APIResponse> createOrganisation(@Valid @RequestBody CreateOrganisationRequest request)
    {
        CreateOrganisationResult result = organisationService.createOrganisation(request);
        return ResponseEntity.created(null).body(new APIResponse());
    }
}
