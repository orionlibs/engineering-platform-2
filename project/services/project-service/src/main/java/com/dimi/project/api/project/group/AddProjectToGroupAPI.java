package com.dimi.project.api.project.group;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.group.member.AddProjectToGroupResult;
import com.dimi.project.project.group.member.ProjectGroupMemberError;
import com.dimi.project.project.group.member.ProjectGroupMemberService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddProjectToGroupAPI
{
    @Autowired ProjectGroupMemberService projectGroupMemberService;


    @PostMapping(value = "/projects/{projectID}/groups/{projectGroupID}")
    public ResponseEntity<APIResponse> addProjectToGroup(@PathVariable(name = "projectID") UUID projectID, @PathVariable(name = "projectGroupID") UUID projectGroupID)
    {
        AddProjectToGroupResult result = projectGroupMemberService.addProjectToGroup(projectID, projectGroupID);
        if(result.getError() != null)
        {
            APIResponse response = APIResponse.ofError(result.getError());
            if(result.getError().getErrorCode().equals(ProjectGroupMemberError.PROJECT_OR_PROJECT_GROUP_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.created(null).body(new APIResponse());
    }
}
