package com.dimi.organisation;

import com.dimi.core.ServiceOperationResult;
import com.dimi.organisation.model.OrganisationModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class CreateOrganisationResult extends ServiceOperationResult
{
    private OrganisationModel organisation;
}
