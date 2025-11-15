package com.dimi.project.project.issue.request;

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
public class CreateIssueRequest extends APIRequest
{
    @NotBlank(message = "code must not be blank")
    private String code;
    @NotBlank(message = "title must not be blank")
    private String title;
    @NotNull(message = "projectID must not be blank")
    private UUID projectID;
}
