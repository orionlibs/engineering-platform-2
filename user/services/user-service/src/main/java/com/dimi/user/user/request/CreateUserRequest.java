package com.dimi.user.user.request;

import com.dimi.core.api.APIRequest;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class CreateUserRequest extends APIRequest
{
    @Email(message = "username must be a valid email address")
    private String username;
}
