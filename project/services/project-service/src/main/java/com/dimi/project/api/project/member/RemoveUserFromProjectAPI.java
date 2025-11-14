package com.dimi.project.api.project.member;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.member.ProjectMemberError;
import com.dimi.project.project.member.ProjectMemberService;
import com.dimi.project.project.member.RemoveUserFromProjectResult;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoveUserFromProjectAPI
{
    @Autowired private ProjectMemberService projectMemberService;


    @DeleteMapping(value = "/projects/{projectID}/members/{userID}")
    public ResponseEntity<APIResponse> removeUserFromProject(@PathVariable(name = "projectID") UUID projectID, @PathVariable(name = "userID") UUID userID)
    {
        RemoveUserFromProjectResult result = projectMemberService.removeUserFromProject(projectID, userID);
        if(result.getError() != null)
        {
            APIResponse response = APIResponse.ofError(result.getError());
            if(result.getError().getErrorCode().equals(ProjectMemberError.PROJECT_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.created(null).body(new APIResponse());
    }
}
