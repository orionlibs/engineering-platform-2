package com.dimi.project.api.project.issue;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.issue.IssueService;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetIssueIDsAssignedToUserAPI
{
    @Autowired private IssueService issueService;


    @GetMapping(value = "/issues/assigned/{userID}")
    public ResponseEntity<APIResponse> getIssueIDsAssignedToUser(@PathVariable(name = "userID") UUID userID)
    {
        List<UUID> issueIDsFound = issueService.getAllIssueIDsAssignedToUser(userID);
        List<String> issueIDs = issueIDsFound.stream().map(UUID::toString).toList();
        APIResponse<ProjectGroupsResponse> response = new APIResponse<>(ProjectGroupsResponse.builder()
                        .issueIDs(issueIDs)
                        .build());
        return ResponseEntity.ok(response);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ProjectGroupsResponse implements Serializable
    {
        private List<String> issueIDs;
    }
}
