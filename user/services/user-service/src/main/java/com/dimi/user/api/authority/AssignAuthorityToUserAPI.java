package com.dimi.user.api.authority;

import com.dimi.core.api.APIResponse;
import com.dimi.user.authority.AssignAuthorityToUserResult;
import com.dimi.user.authority.UserAuthorityService;
import com.dimi.user.authority.request.AssignAuthorityToUserRequest;
import com.dimi.user.user.UserError;
import jakarta.validation.Valid;
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
    @Autowired UserAuthorityService userAuthorityService;


    @PatchMapping(value = "/users/authority-assignments")
    public ResponseEntity<APIResponse> assignAuthorityTUser(@Valid @RequestBody AssignAuthorityToUserRequest request)
    {
        AssignAuthorityToUserResult result = userAuthorityService.assignAuthority(request);
        if(result.getError() != null)
        {
            APIResponse response = APIResponse.ofError(result.getError());
            if(result.getError().getErrorCode().equals(UserError.USER_NOT_FOUND)
                            || result.getError().getErrorCode().equals(UserError.USER_AUTHORITY_NOT_FOUND))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
            }
        }
        return ResponseEntity.ok(new APIResponse());
    }
}
