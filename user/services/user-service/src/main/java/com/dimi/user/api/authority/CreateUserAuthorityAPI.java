package com.dimi.user.api.authority;

import com.dimi.core.api.APIResponse;
import com.dimi.user.authority.CreateAuthorityResult;
import com.dimi.user.authority.UserAuthorityService;
import com.dimi.user.authority.request.CreateUserAuthorityRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CreateUserAuthorityAPI
{
    @Autowired UserAuthorityService userAuthorityService;


    @PostMapping(value = "/users/authorities")
    public ResponseEntity<APIResponse> createUserAuthority(@Valid @RequestBody CreateUserAuthorityRequest request)
    {
        CreateAuthorityResult result = userAuthorityService.createAuthority(request);
        return ResponseEntity.created(null).body(new APIResponse());
    }
}
