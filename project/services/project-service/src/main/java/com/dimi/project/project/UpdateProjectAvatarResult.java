package com.dimi.project.project;

import com.dimi.core.ServiceOperationResult;
import com.dimi.project.model.project.ProjectModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class UpdateProjectAvatarResult extends ServiceOperationResult
{
    private ProjectModel project;
}
