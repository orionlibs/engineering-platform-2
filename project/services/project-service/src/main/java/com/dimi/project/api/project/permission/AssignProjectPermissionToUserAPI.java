package com.dimi.project.api.project.permission;

import com.dimi.core.api.APIResponse;
import com.dimi.project.permission.AssignPermissionToUserResult;
import com.dimi.project.permission.PermissionError;
import com.dimi.project.permission.PermissionService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AssignProjectPermissionToUserAPI
{
    @Autowired PermissionService permissionService;


    @PostMapping(value = "/projects/permissions/{permissionID}/assignees")
    public ResponseEntity<APIResponse> assignProjectPermissionToUser(@PathVariable(name = "permissionID") UUID permissionID, @Valid @RequestBody AssignPermissionToUserRequest request)
    {
        AssignPermissionToUserResult result = permissionService.assignPermissionToUser(permissionID, request);
        if(result.getError() != null)
        {
            APIResponse response = new APIResponse();
            response.setError(result.getError());
            if(result.getError().getErrorCode().equals(PermissionError.PERMISSION_NOT_FOUND))
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
    public static class AssignPermissionToUserRequest implements Serializable
    {
        @NotNull(message = "userID must not be blank")
        private UUID userID;
    }
}
