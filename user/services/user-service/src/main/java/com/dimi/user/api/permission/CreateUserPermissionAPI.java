package com.dimi.user.api.permission;

import com.dimi.core.api.APIResponse;
import com.dimi.user.permission.CreatePermissionResult;
import com.dimi.user.permission.UserPermissionService;
import com.dimi.user.permission.request.CreateUserPermissionRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CreateUserPermissionAPI
{
    @Autowired UserPermissionService userPermissionService;


    @PostMapping(value = "/users/permissions")
    public ResponseEntity<APIResponse> createUserPermission(@Valid @RequestBody CreateUserPermissionRequest request)
    {
        CreatePermissionResult result = userPermissionService.createPermission(request);
        return ResponseEntity.created(null).body(new APIResponse());
    }
}
