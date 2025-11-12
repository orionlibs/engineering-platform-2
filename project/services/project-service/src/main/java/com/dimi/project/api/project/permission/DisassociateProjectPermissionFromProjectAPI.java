package com.dimi.project.api.project.permission;

import com.dimi.core.api.APIResponse;
import com.dimi.project.permission.DisassociatePermissionFromProjectResult;
import com.dimi.project.permission.PermissionService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class DisassociateProjectPermissionFromProjectAPI
{
    @Autowired PermissionService permissionService;


    @DeleteMapping(value = "/projects/{projectID}/permissions/{permissionID}/associatees")
    public ResponseEntity<APIResponse> disassociateProjectPermissionFromProject(@PathVariable(name = "projectID") UUID projectID, @PathVariable(name = "permissionID") UUID permissionID)
    {
        DisassociatePermissionFromProjectResult result = permissionService.disassociatePermissionFromProject(projectID, permissionID);
        return ResponseEntity.ok(new APIResponse());
    }
}
