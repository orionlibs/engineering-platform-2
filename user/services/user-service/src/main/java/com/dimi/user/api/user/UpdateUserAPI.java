package com.dimi.user.api.user;

import com.dimi.core.api.APIResponse;
import com.dimi.user.user.UpdateUserResult;
import com.dimi.user.user.UserError;
import com.dimi.user.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UpdateUserAPI
{
    @Autowired UserService userService;


    @PutMapping(value = "/users/{userID}")
    public ResponseEntity<APIResponse> updateUser(@PathVariable(name = "userID") UUID userID, @Valid @RequestBody UpdateUserRequest request)
    {
        UpdateUserResult result = userService.updateUser(userID, request);
        if(result.getError() != null)
        {
            APIResponse response = APIResponse.ofError(result.getError());
            if(result.getError().getErrorCode().equals(UserError.USER_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.ok(new APIResponse());
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class UpdateUserRequest implements Serializable
    {
        @Email(message = "username must be a valid email address")
        private String username;
    }
}
