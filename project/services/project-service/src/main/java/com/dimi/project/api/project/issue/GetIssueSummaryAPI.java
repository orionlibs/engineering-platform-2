package com.dimi.project.api.project.issue;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.issue.IssueService;
import java.io.Serializable;
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
public class GetIssueSummaryAPI
{
    @Autowired private IssueService issueService;


    @GetMapping(value = "/issues/{issueID}")
    public ResponseEntity<APIResponse> getIssueSummary(@PathVariable(name = "issueID") UUID issueID)
    {
        APIResponse<IssueResponse> response = issueService.getByID(issueID).map(p -> new APIResponse<>(IssueResponse.builder()
                        .issueID(p.getId().toString())
                        .projectID(p.getProject().getId().toString())
                        .code(p.getCode())
                        .title(p.getTitle())
                        .build())).orElse(new APIResponse<>(IssueResponse.builder().build()));
        return ResponseEntity.ok(response);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class IssueResponse implements Serializable
    {
        private String issueID;
        private String projectID;
        private String code;
        private String title;
    }
}
