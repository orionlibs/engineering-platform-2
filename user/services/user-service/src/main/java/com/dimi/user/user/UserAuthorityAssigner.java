package com.dimi.user.user;

import com.dimi.core.exception.AError;
import com.dimi.user.api.authority.AssignAuthorityToUserAPI.AssignAuthorityToUserRequest;
import com.dimi.user.authority.UserAuthorityService;
import com.dimi.user.model.UserAuthorityModel;
import com.dimi.user.model.UserModel;
import com.dimi.user.model.UsersDAO;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserAuthorityAssigner
{
    @Autowired private UserAuthorityService userAuthorityService;
    @Autowired private UsersDAO dao;


    @Transactional
    AssignAuthorityToUserResult assignAuthority(AssignAuthorityToUserRequest request)
    {
        Optional<UserModel> wrap = dao.findById(request.getUserID());
        if(wrap.isPresent())
        {
            UserModel user = wrap.get();
            Optional<UserAuthorityModel> authorityWrap = userAuthorityService.getByAuthority(request.getAuthority());
            if(authorityWrap.isPresent())
            {
                UserAuthorityModel authority = authorityWrap.get();
                boolean authorityFound = user.getAuthorities()
                                .stream()
                                .anyMatch(a -> a.getId().equals(authority.getId()));
                if(authorityFound)
                {
                    AError error = new AError<>();
                    error.setErrorCode(UserError.USER_ALREADY_HAS_AUTHORITY);
                    return AssignAuthorityToUserResult.builder()
                                    .error(error)
                                    .build();
                }
                else
                {
                    user.getAuthorities().add(authority);
                    return AssignAuthorityToUserResult.builder()
                                    .user(dao.save(user))
                                    .build();
                }
            }
            else
            {
                AError error = new AError<>();
                error.setErrorCode(UserError.USER_AUTHORITY_NOT_FOUND);
                return AssignAuthorityToUserResult.builder()
                                .error(error)
                                .build();
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(UserError.USER_NOT_FOUND);
            return AssignAuthorityToUserResult.builder()
                            .error(error)
                            .build();
        }
    }
}
