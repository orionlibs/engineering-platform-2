package com.dimi.user.user;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.user.api.user.CreateUserAPI.NewUserRequest;
import com.dimi.user.authority.UserAuthority;
import com.dimi.user.authority.UserAuthorityService;
import com.dimi.user.model.authority.UserAuthorityModel;
import com.dimi.user.model.user.UserModel;
import com.dimi.user.model.user.UsersDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserCreator
{
    @Autowired private UserAuthorityService userAuthorityService;
    @Autowired private UsersDAO dao;


    @Transactional
    CreateUserResult createUser(NewUserRequest request)
    {
        List<UserAuthorityModel> authorities = new ArrayList<>();
        Optional<UserAuthorityModel> authorityWrap = userAuthorityService.getByAuthority(UserAuthority.USER.name());
        if(authorityWrap.isPresent())
        {
            authorities.add(authorityWrap.get());
            UserModel user = new UserModel(request.getUsername(), authorities);
            try
            {
                UserModel saved = dao.save(user);
                dao.flush();
                return CreateUserResult.builder()
                                .user(saved)
                                .build();
            }
            catch(DataIntegrityViolationException e)
            {
                throw new DuplicateRecordException(UserError.USER_ALREADY_EXISTS);
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(UserError.DEFAULT_USER_AUTHORITY_NOT_FOUND);
            return CreateUserResult.builder()
                            .error(error)
                            .build();
        }
    }
}
