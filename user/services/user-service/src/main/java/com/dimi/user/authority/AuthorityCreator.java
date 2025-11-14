package com.dimi.user.authority;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.user.api.authority.CreateUserAuthorityAPI.NewUserAuthorityRequest;
import com.dimi.user.model.user.authority.UserAuthoritiesDAO;
import com.dimi.user.model.user.authority.UserAuthorityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class AuthorityCreator
{
    @Autowired private UserAuthoritiesDAO dao;


    @Transactional
    CreateAuthorityResult createAuthority(NewUserAuthorityRequest request)
    {
        UserAuthorityModel model = new UserAuthorityModel(request.getAuthority());
        try
        {
            UserAuthorityModel saved = dao.save(model);
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
}
