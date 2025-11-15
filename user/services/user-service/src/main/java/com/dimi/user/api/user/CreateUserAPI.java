package com.dimi.user.api.user;

import com.dimi.core.api.APIResponse;
import com.dimi.user.user.CreateUserResult;
import com.dimi.user.user.UserError;
import com.dimi.user.user.UserService;
import com.dimi.user.user.request.CreateUserRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CreateUserAPI
{
    @Autowired private UserService userService;


    @PostMapping(value = "/users")
    public ResponseEntity<APIResponse> createUser(@Valid @RequestBody CreateUserRequest request)
    {
        CreateUserResult result = userService.createUser(request);
        if(result.getError() != null)
        {
            APIResponse response = APIResponse.ofError(result.getError());
            if(result.getError().getErrorCode().equals(UserError.DEFAULT_USER_AUTHORITY_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.created(null).body(new APIResponse());
    }
}
