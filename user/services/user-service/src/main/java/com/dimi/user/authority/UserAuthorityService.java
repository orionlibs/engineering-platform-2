package com.dimi.user.authority;

import com.dimi.user.api.authority.AssignAuthorityToUserAPI.AssignAuthorityToUserRequest;
import com.dimi.user.api.authority.CreateUserAuthorityAPI.NewUserAuthorityRequest;
import com.dimi.user.api.authority.UnassignAuthorityToUserAPI.UnassignAuthorityToUserRequest;
import com.dimi.user.model.user.authority.UserAuthoritiesDAO;
import com.dimi.user.model.user.authority.UserAuthorityModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAuthorityService
{
    @Autowired private AuthorityCreator authorityCreator;
    @Autowired private UserAuthorityAssigner userAuthorityAssigner;
    @Autowired private UserAuthorityUnassigner userAuthorityUnassigner;
    @Autowired private UserAuthoritiesDAO dao;


    @Transactional
    public CreateAuthorityResult createAuthority(NewUserAuthorityRequest request)
    {
        return authorityCreator.createAuthority(request);
    }


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


    @Transactional
    public UserAuthorityModel save(UserAuthorityModel model)
    {
        return dao.save(model);
    }


    public Optional<UserAuthorityModel> getByID(UUID id)
    {
        return dao.findById(id);
    }


    public Optional<UserAuthorityModel> getByAuthority(String authority)
    {
        return dao.findByAuthority(authority);
    }


    public List<UserAuthorityModel> getAll()
    {
        return dao.findAll();
    }


    public void deleteAll()
    {
        dao.deleteAll();
    }
}
