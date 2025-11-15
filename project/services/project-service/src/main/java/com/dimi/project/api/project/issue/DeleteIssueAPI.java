package com.dimi.project.api.project.issue;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.issue.IssueService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class DeleteIssueAPI
{
    @Autowired private IssueService issueService;


    @DeleteMapping(value = "/issues/{issueID}")
    public ResponseEntity<APIResponse> deleteIssue(@PathVariable(name = "issueID") UUID issueID)
    {
        issueService.delete(issueID);
        return ResponseEntity.ok(new APIResponse());
    }
}
