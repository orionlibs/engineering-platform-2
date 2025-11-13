package com.dimi.user.authority;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.user.api.authority.CreateUserAuthorityAPI.NewUserAuthorityRequest;
import com.dimi.user.model.UserAuthoritiesDAO;
import com.dimi.user.model.UserAuthorityModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAuthorityService
{
    @Autowired private UserAuthoritiesDAO dao;


    @Transactional
    public CreateAuthorityResult createAuthority(NewUserAuthorityRequest request)
    {
        UserAuthorityModel model = new UserAuthorityModel(request.getAuthority());
        try
        {
            UserAuthorityModel saved = save(model);
            dao.flush();
            return CreateAuthorityResult.builder()
                            .authority(saved)
                            .build();
        }
        catch(DataIntegrityViolationException e)
        {
            throw new DuplicateRecordException(request.getAuthority() + " already exists");
        }
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
