package com.dimi.organisation.api;

import com.dimi.core.api.APIResponse;
import com.dimi.organisation.OrganisationService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class GetOrganisationsAPI
{
    @Autowired private OrganisationService organisationService;


    @GetMapping(value = "/organisations")
    public ResponseEntity<APIResponse> getOrganisations()
    {
        List<Organisation> organisations = new ArrayList<>();
        organisationService.getAll().forEach(o -> organisations.add(Organisation.builder()
                        .organisationID(o.getId().toString())
                        .name(o.getName())
                        .ownerUserID(o.getOwnerUserID().toString())
                        .build()));
        APIResponse<OrganisationResponse> response = new APIResponse<>(OrganisationResponse.builder()
                        .organisations(organisations)
                        .build());
        return ResponseEntity.ok(response);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class OrganisationResponse implements Serializable
    {
        private List<Organisation> organisations;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class Organisation implements Serializable
    {
        private String organisationID;
        private String name;
        private String ownerUserID;
    }
}
