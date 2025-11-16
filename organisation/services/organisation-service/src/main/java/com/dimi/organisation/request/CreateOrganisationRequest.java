package com.dimi.organisation.request;

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
public class CreateOrganisationRequest extends APIRequest
{
    @NotBlank(message = "name must not be blank")
    private String name;
    @NotNull(message = "ownerUserID must not be blank")
    private UUID ownerUserID;
}
