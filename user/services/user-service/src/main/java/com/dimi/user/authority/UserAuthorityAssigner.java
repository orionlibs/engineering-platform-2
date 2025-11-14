package com.dimi.user.authority;

import com.dimi.core.exception.AError;
import com.dimi.user.api.authority.AssignAuthorityToUserAPI.AssignAuthorityToUserRequest;
import com.dimi.user.model.user.authority.UserAuthoritiesDAO;
import com.dimi.user.model.user.authority.UserAuthorityModel;
import com.dimi.user.model.user.UserModel;
import com.dimi.user.model.user.UsersDAO;
import com.dimi.user.user.UserError;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserAuthorityAssigner
{
    @Autowired private UsersDAO usersDAO;
    @Autowired private UserAuthoritiesDAO userAuthoritiesDAO;


    @Transactional
    AssignAuthorityToUserResult assignAuthority(AssignAuthorityToUserRequest request)
    {
        Optional<UserModel> wrap = usersDAO.findById(request.getUserID());
        if(wrap.isPresent())
        {
            UserModel user = wrap.get();
            Optional<UserAuthorityModel> authorityWrap = userAuthoritiesDAO.findByAuthority(request.getAuthority());
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
                                    .user(usersDAO.save(user))
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
