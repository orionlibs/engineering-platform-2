package com.dimi.user.api.permission;

import com.dimi.core.api.APIResponse;
import com.dimi.user.user.RevokePermissionForUserResult;
import com.dimi.user.user.UserError;
import com.dimi.user.user.UserService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class RevokePermissionForUserAPI
{
    @Autowired UserService userService;


    @DeleteMapping(value = "/users/{userID}/permissions/{permissionID}")
    public ResponseEntity<APIResponse> revokePermissionForUser(@PathVariable(name = "userID") UUID userID, @PathVariable(name = "permissionID") UUID permissionID)
    {
        RevokePermissionForUserResult result = userService.revokePermission(userID, permissionID);
        if(result.getError() != null)
        {
            APIResponse response = new APIResponse();
            response.setError(result.getError());
            if(result.getError().getErrorCode().equals(UserError.USER_NOT_FOUND)
                            || result.getError().getErrorCode().equals(UserError.USER_PERMISSION_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.ok(new APIResponse());
    }
}
