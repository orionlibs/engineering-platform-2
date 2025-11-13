package com.dimi.project.api.project.permission;

import com.dimi.core.api.APIResponse;
import com.dimi.project.permission.AssociatePermissionWithProjectResult;
import com.dimi.project.permission.PermissionError;
import com.dimi.project.permission.PermissionService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AssociateProjectPermissionWithProjectAPI
{
    @Autowired PermissionService permissionService;


    @PostMapping(value = "/projects/{projectID}/permissions/{permissionID}/associatees")
    public ResponseEntity<APIResponse> associateProjectPermissionWithProject(@PathVariable(name = "projectID") UUID projectID, @PathVariable(name = "permissionID") UUID permissionID)
    {
        AssociatePermissionWithProjectResult result = permissionService.associatePermissionWithProject(projectID, permissionID);
        if(result.getError() != null)
        {
            APIResponse response = new APIResponse();
            response.setError(result.getError());
            if(result.getError().getErrorCode().equals(PermissionError.PERMISSION_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
            else if(result.getError().getErrorCode().equals(PermissionError.PROJECT_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.created(null).body(new APIResponse());
    }
}
