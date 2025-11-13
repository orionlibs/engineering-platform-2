package com.dimi.user.api.authority;

import com.dimi.core.api.APIResponse;
import com.dimi.user.user.AssignAuthorityToUserResult;
import com.dimi.user.user.UserError;
import com.dimi.user.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AssignAuthorityToUserAPI
{
    @Autowired UserService userService;


    @PatchMapping(value = "/users/authority-assignments")
    public ResponseEntity<APIResponse> assignAuthorityTUser(@Valid @RequestBody AssignAuthorityToUserRequest request)
    {
        AssignAuthorityToUserResult result = userService.assignAuthority(request);
        if(result.getError() != null)
        {
            APIResponse response = new APIResponse();
            response.setError(result.getError());
            if(result.getError().getErrorCode().equals(UserError.USER_NOT_FOUND)
                            || result.getError().getErrorCode().equals(UserError.USER_AUTHORITY_NOT_FOUND))
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
    public static class AssignAuthorityToUserRequest implements Serializable
    {
        @NotBlank(message = "authority must not be blank")
        private String authority;
        @NotNull(message = "userID must not be blank")
        private UUID userID;
    }
}
