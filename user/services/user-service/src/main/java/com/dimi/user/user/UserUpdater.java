package com.dimi.user.user;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.user.api.user.UpdateUserAPI.UpdateUserRequest;
import com.dimi.user.model.user.UserModel;
import com.dimi.user.model.user.UsersDAO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserUpdater
{
    @Autowired private UsersDAO dao;


    @Transactional
    UpdateUserResult updateUser(UUID userID, UpdateUserRequest request)
    {
        Optional<UserModel> opt = dao.findById(userID);
        if(opt.isPresent())
        {
            UserModel user = opt.get();
            user.setUsername(request.getUsername());
            try
            {
                UserModel saved = dao.save(user);
                dao.flush();
                return UpdateUserResult.builder()
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
            error.setErrorCode(UserError.USER_NOT_FOUND);
            return UpdateUserResult.builder()
                            .error(error)
                            .build();
        }
    }
}
