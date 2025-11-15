package com.dimi.project.api.project.issue;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.issue.IssueService;
import com.dimi.project.project.issue.UnassignIssueResult;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UnassignIssueAPI
{
    @Autowired private IssueService issueService;


    @DeleteMapping(value = "/issues/{issueID}/assignments")
    public ResponseEntity<APIResponse> unassignIssue(@PathVariable(name = "issueID") UUID issueID)
    {
        UnassignIssueResult result = issueService.unassignIssue(issueID);
        return ResponseEntity.ok(new APIResponse());
    }
}
