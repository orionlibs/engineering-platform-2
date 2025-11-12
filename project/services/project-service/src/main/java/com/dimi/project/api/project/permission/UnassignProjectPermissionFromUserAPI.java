package com.dimi.project.api.project.permission;

import com.dimi.core.api.APIResponse;
import com.dimi.project.permission.PermissionService;
import com.dimi.project.permission.UnassignPermissionFromUserResult;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UnassignProjectPermissionFromUserAPI
{
    @Autowired PermissionService permissionService;


    @DeleteMapping(value = "/projects/permissions/{permissionID}/assignees/{userID}")
    public ResponseEntity<APIResponse> unassignProjectPermissionFromUser(@PathVariable(name = "permissionID") UUID permissionID, @PathVariable(name = "userID") UUID userID)
    {
        UnassignPermissionFromUserResult result = permissionService.unassignPermissionFromUser(permissionID, userID);
        return ResponseEntity.ok(new APIResponse());
    }
}
