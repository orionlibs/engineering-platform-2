package com.dimi.project.api.project.issue;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.issue.AssignIssueResult;
import com.dimi.project.project.issue.IssueService;
import com.dimi.project.project.issue.request.AssignIssueRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AssignIssueAPI
{
    @Autowired private IssueService issueService;


    @PostMapping(value = "/issues/{issueID}/assignments")
    public ResponseEntity<APIResponse> assignIssue(@PathVariable(name = "issueID") UUID issueID, @Valid @RequestBody AssignIssueRequest request)
    {
        AssignIssueResult result = issueService.assignIssue(issueID, request.getUserID());
        return ResponseEntity.ok(new APIResponse());
    }
}
