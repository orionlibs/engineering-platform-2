package com.dimi.user.authority;

import com.dimi.core.ServiceOperationResult;
import com.dimi.user.model.authority.UserAuthorityModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class CreateAuthorityResult extends ServiceOperationResult
{
    private UserAuthorityModel authority;
}
