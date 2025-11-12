package com.dimi.user.api.permission;

import com.dimi.core.api.APIResponse;
import com.dimi.user.permission.CreatePermissionResult;
import com.dimi.user.permission.UserPermissionError;
import com.dimi.user.permission.UserPermissionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<APIResponse> createUserPermission(@Valid @RequestBody NewUserPermissionRequest request)
    {
        CreatePermissionResult result = userPermissionService.createPermission(request);
        if(result.getError() != null)
        {
            APIResponse response = new APIResponse();
            response.setError(result.getError());
            if(result.getError().getErrorCode().equals(UserPermissionError.USER_PERMISSION_ALREADY_EXISTS))
            {
                return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(response);
            }
        }
        return ResponseEntity.created(null).body(new APIResponse());
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class NewUserPermissionRequest implements Serializable
    {
        @NotBlank(message = "name must not be blank")
        private String name;
        private String description;
    }
}
