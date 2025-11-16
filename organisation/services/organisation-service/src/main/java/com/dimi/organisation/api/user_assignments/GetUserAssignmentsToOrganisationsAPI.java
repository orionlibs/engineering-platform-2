package com.dimi.organisation.api.user_assignments;

import com.dimi.core.api.APIResponse;
import com.dimi.organisation.user_assignments.UserAssignmentToOrganisationService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class GetUserAssignmentsToOrganisationsAPI
{
    @Autowired private UserAssignmentToOrganisationService userAssignmentToOrganisationService;


    @GetMapping(value = "/organisations/user-assignments/{userID}")
    public ResponseEntity<APIResponse> getUserAssignmentsToOrganisations(@PathVariable(name = "userID") UUID userID)
    {
        List<Organisation> organisations = new ArrayList<>();
        userAssignmentToOrganisationService.getAllByUserID(userID).forEach(o -> organisations.add(Organisation.builder()
                        .organisationID(o.getOrganisation().getId().toString())
                        .name(o.getOrganisation().getName())
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
    }
}
