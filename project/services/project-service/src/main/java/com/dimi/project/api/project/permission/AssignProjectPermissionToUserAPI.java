package com.dimi.project.api.project.permission;

import com.dimi.core.api.APIResponse;
import com.dimi.project.permission.AssignPermissionToUserResult;
import com.dimi.project.permission.PermissionError;
import com.dimi.project.permission.PermissionService;
import com.dimi.project.permission.request.AssignProjectPermissionToUserRequest;
import jakarta.validation.Valid;
import java.util.UUID;
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
    @Autowired private PermissionService permissionService;


    @PostMapping(value = "/projects/permissions/{permissionID}/assignees")
    public ResponseEntity<APIResponse> assignProjectPermissionToUser(@PathVariable(name = "permissionID") UUID permissionID, @Valid @RequestBody AssignProjectPermissionToUserRequest request)
    {
        AssignPermissionToUserResult result = permissionService.assignPermissionToUser(permissionID, request.getUserID());
        if(result.getError() != null)
        {
            APIResponse response = APIResponse.ofError(result.getError());
            if(result.getError().getErrorCode().equals(PermissionError.PERMISSION_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.created(null).body(new APIResponse());
    }
}
