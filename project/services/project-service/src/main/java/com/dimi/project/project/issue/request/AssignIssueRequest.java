package com.dimi.project.project.issue.request;

import com.dimi.core.api.APIRequest;
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
public class AssignIssueRequest extends APIRequest
{
    @NotNull(message = "userID must not be blank")
    private UUID userID;
}
