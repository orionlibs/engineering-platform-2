package com.dimi.project.user_group.request;

import com.dimi.core.api.APIRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class CreateUserGroupRequest extends APIRequest
{
    @NotBlank(message = "name must not be blank")
    private String name;
    private String description;
}
