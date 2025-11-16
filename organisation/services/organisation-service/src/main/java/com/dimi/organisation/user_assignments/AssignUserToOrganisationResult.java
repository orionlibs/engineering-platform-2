package com.dimi.organisation.user_assignments;

import com.dimi.core.ServiceOperationResult;
import com.dimi.organisation.model.UserAssignmentToOrganisationModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class AssignUserToOrganisationResult extends ServiceOperationResult
{
    private UserAssignmentToOrganisationModel userAssignmentToOrganisation;
}
