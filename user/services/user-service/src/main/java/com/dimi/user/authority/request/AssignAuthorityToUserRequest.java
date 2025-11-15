package com.dimi.user.authority.request;

import com.dimi.core.api.APIRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class AssignAuthorityToUserRequest extends APIRequest
{
    @NotBlank(message = "authority must not be blank")
    private String authority;
    @NotNull(message = "userID must not be blank")
    private UUID userID;
}
