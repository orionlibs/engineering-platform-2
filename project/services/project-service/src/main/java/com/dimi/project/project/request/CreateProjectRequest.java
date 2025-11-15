package com.dimi.project.project.request;

import com.dimi.core.api.APIRequest;
import com.dimi.project.model.project.ProjectType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class CreateProjectRequest extends APIRequest
{
    @NotBlank(message = "name must not be blank")
    private String name;
    @NotBlank(message = "code must not be blank")
    private String code;
    private String description;
    @NotNull(message = "type cannot be null")
    private ProjectType.Type type;
    private String avatarURL;
}
