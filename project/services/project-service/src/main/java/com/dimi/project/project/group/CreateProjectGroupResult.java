package com.dimi.project.project.group;

import com.dimi.core.ServiceOperationResult;
import com.dimi.project.model.project.group.ProjectGroupModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class CreateProjectGroupResult extends ServiceOperationResult
{
    private ProjectGroupModel projectGroup;
}
