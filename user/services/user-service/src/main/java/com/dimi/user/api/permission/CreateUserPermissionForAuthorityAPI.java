package com.dimi.user.api.permission;

import com.dimi.core.api.APIResponse;
import com.dimi.user.permission.CreatePermissionForAuthorityResult;
import com.dimi.user.permission.UserPermissionPerAuthorityError;
import com.dimi.user.permission.UserPermissionsPerAuthorityService;
import com.dimi.user.permission.request.CreateUserPermissionForAuthorityRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CreateUserPermissionForAuthorityAPI
{
    @Autowired UserPermissionsPerAuthorityService userPermissionsPerAuthorityService;


    @PostMapping(value = "/users/permissions-for-authority")
    public ResponseEntity<APIResponse> createUserPermissionForAuthority(@Valid @RequestBody CreateUserPermissionForAuthorityRequest request)
    {
        CreatePermissionForAuthorityResult result = userPermissionsPerAuthorityService.createPermissionForAuthority(request);
        if(result.getError() != null)
        {
            APIResponse response = APIResponse.ofError(result.getError());
            if(result.getError().getErrorCode().equals(UserPermissionPerAuthorityError.USER_PERMISSION_OR_AUTHORITY_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.created(null).body(new APIResponse());
    }
}
