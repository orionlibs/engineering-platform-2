package com.dimi.organisation.api;

import com.dimi.core.api.APIResponse;
import com.dimi.organisation.OrganisationService;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class GetOrganisationAPI
{
    @Autowired private OrganisationService organisationService;


    @GetMapping(value = "/organisations/{organisationID}")
    public ResponseEntity<APIResponse> getOrganisation(@PathVariable(name = "organisationID") UUID organisationID)
    {
        APIResponse<OrganisationResponse> response = organisationService.getByID(organisationID).map(o -> new APIResponse<>(OrganisationResponse.builder()
                        .organisationID(o.getId().toString())
                        .name(o.getName())
                        .ownerUserID(o.getOwnerUserID().toString())
                        .build())).orElse(new APIResponse<>(OrganisationResponse.builder().build()));
        return ResponseEntity.ok(response);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class OrganisationResponse implements Serializable
    {
        private String organisationID;
        private String name;
        private String ownerUserID;
    }
}
