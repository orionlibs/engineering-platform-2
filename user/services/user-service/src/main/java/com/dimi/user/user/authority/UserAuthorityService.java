package com.dimi.user.user.authority;

import com.dimi.user.api.authority.AssignAuthorityToUserAPI.AssignAuthorityToUserRequest;
import com.dimi.user.api.authority.UnassignAuthorityToUserAPI.UnassignAuthorityToUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAuthorityService
{
    @Autowired private UserAuthorityAssigner userAuthorityAssigner;
    @Autowired private UserAuthorityUnassigner userAuthorityUnassigner;


    @Transactional
    public AssignAuthorityToUserResult assignAuthority(AssignAuthorityToUserRequest request)
    {
        return userAuthorityAssigner.assignAuthority(request);
    }


    @Transactional
    public UnassignAuthorityToUserResult unassignAuthority(UnassignAuthorityToUserRequest request)
    {
        return userAuthorityUnassigner.unassignAuthority(request);
    }
}
