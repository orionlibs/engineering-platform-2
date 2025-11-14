package com.dimi.project.api.project.member;

import com.dimi.core.api.APIResponse;
import com.dimi.project.project.member.AddUserToProjectResult;
import com.dimi.project.project.member.ProjectMemberError;
import com.dimi.project.project.member.ProjectMemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddUserToProjectAPI
{
    @Autowired private ProjectMemberService projectMemberService;


    @PostMapping(value = "/projects/{projectID}/members")
    public ResponseEntity<APIResponse> addUserToProject(@PathVariable(name = "projectID") UUID projectID, @Valid @RequestBody AddUserToProjectRequest request)
    {
        AddUserToProjectResult result = projectMemberService.addUserToProject(projectID, request.getUserID());
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


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class AddUserToProjectRequest implements Serializable
    {
        @NotNull(message = "userID must not be blank")
        private UUID userID;
    }
}
