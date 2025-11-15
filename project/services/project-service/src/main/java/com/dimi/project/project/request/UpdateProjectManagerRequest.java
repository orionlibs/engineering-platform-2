package com.dimi.project.project.request;

import com.dimi.core.api.APIRequest;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class UpdateProjectManagerRequest extends APIRequest
{
    @NotBlank(message = "managerUserID must not be blank")
    private UUID managerUserID;
}
