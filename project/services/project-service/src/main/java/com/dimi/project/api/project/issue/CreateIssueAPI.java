package com.dimi.project.api.project.issue;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.issue.CreateIssueResult;
import com.dimi.project.project.issue.IssueService;
import com.dimi.project.project.issue.request.CreateIssueRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CreateIssueAPI
{
    @Autowired private IssueService issueService;


    @PostMapping(value = "/issues")
    public ResponseEntity<APIResponse> createIssue(@Valid @RequestBody CreateIssueRequest request)
    {
        CreateIssueResult result = issueService.createIssue(request);
        return ResponseEntity.created(null).body(new APIResponse());
    }
}
