package com.dimi.user.api.permission;

import com.dimi.core.api.APIResponse;
import com.dimi.user.user.GrantPermissionToUserResult;
import com.dimi.user.user.UserError;
import com.dimi.user.user.UserService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class GrantPermissionToUserAPI
{
    @Autowired UserService userService;


    @PatchMapping(value = "/users/{userID}/permissions/{permissionID}")
    public ResponseEntity<APIResponse> grantPermissionToUser(@PathVariable(name = "userID") UUID userID, @PathVariable(name = "permissionID") UUID permissionID)
    {
        GrantPermissionToUserResult result = userService.grantPermission(userID, permissionID);
        if(result.getError() != null)
        {
            APIResponse response = new APIResponse();
            response.setError(result.getError());
            if(result.getError().getErrorCode().equals(UserError.USER_NOT_FOUND)
                            || result.getError().getErrorCode().equals(UserError.USER_AUTHORITY_NOT_FOUND)
                            || result.getError().getErrorCode().equals(UserError.USER_PERMISSION_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.ok(new APIResponse());
    }
}
