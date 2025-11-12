package com.dimi.user.user;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.core.exception.AError;
import com.dimi.user.api.authority.AssignAuthorityToUserAPI.AssignAuthorityToUserRequest;
import com.dimi.user.api.authority.UnassignAuthorityToUserAPI.UnassignAuthorityToUserRequest;
import com.dimi.user.api.user.CreateUserAPI.NewUserRequest;
import com.dimi.user.api.user.UpdateUserAPI.UpdateUserRequest;
import com.dimi.user.authority.UserAuthority;
import com.dimi.user.authority.UserAuthorityService;
import com.dimi.user.model.UserAuthorityModel;
import com.dimi.user.model.UserModel;
import com.dimi.user.model.UserPermissionsPerAuthorityModel;
import com.dimi.user.model.UsersDAO;
import com.dimi.user.permission.UserPermissionsPerAuthorityService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService
{
    @Autowired private UserAuthorityService userAuthorityService;
    @Autowired private UserPermissionsPerAuthorityService userPermissionsPerAuthorityService;
    @Autowired private UsersDAO dao;


    @Transactional
    public CreateUserResult createUser(NewUserRequest request)
    {
        List<UserAuthorityModel> authorities = new ArrayList<>();
        Optional<UserAuthorityModel> authorityWrap = userAuthorityService.getByAuthority(UserAuthority.USER.name());
        if(authorityWrap.isPresent())
        {
            authorities.add(authorityWrap.get());
            UserModel user = new UserModel(request.getUsername(), authorities);
            try
            {
                return CreateUserResult.builder()
                                .user(save(user))
                                .build();
            }
            catch(DuplicateRecordException e)
            {
                AError error = new AError<>();
                error.setErrorCode(UserError.USER_ALREADY_EXISTS);
                error.setErrorMessage(request.getUsername() + " already exists");
                return CreateUserResult.builder()
                                .error(error)
                                .build();
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


    @Transactional
    public UpdateUserResult updateUser(UUID userID, UpdateUserRequest request)
    {
        var opt = getByID(userID);
        if(opt.isPresent())
        {
            UserModel user = opt.get();
            user.setUsername(request.getUsername());
            try
            {
                return UpdateUserResult.builder()
                                .user(save(user))
                                .build();
            }
            catch(DuplicateRecordException e)
            {
                AError error = new AError<>();
                error.setErrorCode(UserError.USER_ALREADY_EXISTS);
                error.setErrorMessage(request.getUsername() + " already exists");
                return UpdateUserResult.builder()
                                .error(error)
                                .build();
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


    @Transactional
    public AssignAuthorityToUserResult assignAuthority(AssignAuthorityToUserRequest request)
    {
        Optional<UserModel> wrap = getByID(request.getUserID());
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
                                    .user(save(user))
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


    @Transactional
    public UnassignAuthorityToUserResult unassignAuthority(UnassignAuthorityToUserRequest request)
    {
        Optional<UserModel> wrap = getByID(request.getUserID());
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
                    user.getAuthorities().remove(authority);
                    return UnassignAuthorityToUserResult.builder()
                                    .user(save(user))
                                    .build();
                }
                else
                {
                    AError error = new AError<>();
                    error.setErrorCode(UserError.USER_AUTHORITY_NOT_FOUND);
                    return UnassignAuthorityToUserResult.builder()
                                    .error(error)
                                    .build();
                }
            }
            else
            {
                AError error = new AError<>();
                error.setErrorCode(UserError.USER_AUTHORITY_NOT_FOUND);
                return UnassignAuthorityToUserResult.builder()
                                .error(error)
                                .build();
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(UserError.USER_NOT_FOUND);
            return UnassignAuthorityToUserResult.builder()
                            .error(error)
                            .build();
        }
    }


    @Transactional
    public GrantPermissionToUserResult grantPermission(UUID userID, UUID permissionID)
    {
        Optional<UserModel> wrap = getByID(userID);
        if(wrap.isPresent())
        {
            UserModel user = wrap.get();
            boolean userAlreadyHasPermission = user.getPermissions()
                            .stream()
                            .anyMatch(p -> p.getId().equals(permissionID));
            if(userAlreadyHasPermission)
            {
                AError error = new AError<>();
                error.setErrorCode(UserError.USER_ALREADY_HAS_PERMISSION);
                return GrantPermissionToUserResult.builder()
                                .error(error)
                                .build();
            }
            else
            {
                Optional<UserPermissionsPerAuthorityModel> permissionWrap = userPermissionsPerAuthorityService.getByID(permissionID);
                if(permissionWrap.isPresent())
                {
                    UserPermissionsPerAuthorityModel permissionModel = permissionWrap.get();
                    boolean authorityFound = user.getAuthorities()
                                    .stream()
                                    .anyMatch(a -> a.getId().equals(permissionModel.getAuthority().getId()));
                    if(authorityFound)
                    {
                        user.getPermissions().add(permissionModel);
                        return GrantPermissionToUserResult.builder()
                                        .user(save(user))
                                        .build();
                    }
                    else
                    {
                        AError error = new AError<>();
                        error.setErrorCode(UserError.USER_AUTHORITY_NOT_FOUND);
                        return GrantPermissionToUserResult.builder()
                                        .error(error)
                                        .build();
                    }
                }
                else
                {
                    AError error = new AError<>();
                    error.setErrorCode(UserError.USER_PERMISSION_NOT_FOUND);
                    return GrantPermissionToUserResult.builder()
                                    .error(error)
                                    .build();
                }
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(UserError.USER_NOT_FOUND);
            return GrantPermissionToUserResult.builder()
                            .error(error)
                            .build();
        }
    }


    @Transactional
    public RevokePermissionForUserResult revokePermission(UUID userID, UUID permissionID)
    {
        Optional<UserModel> wrap = getByID(userID);
        if(wrap.isPresent())
        {
            UserModel user = wrap.get();
            boolean userAlreadyHasPermission = user.getPermissions()
                            .stream()
                            .anyMatch(p -> p.getId().equals(permissionID));
            if(userAlreadyHasPermission)
            {
                Optional<UserPermissionsPerAuthorityModel> permissionWrap = userPermissionsPerAuthorityService.getByID(permissionID);
                if(permissionWrap.isPresent())
                {
                    UserPermissionsPerAuthorityModel permissionModel = permissionWrap.get();
                    user.getPermissions().remove(permissionModel);
                    return RevokePermissionForUserResult.builder()
                                    .user(save(user))
                                    .build();
                }
                else
                {
                    AError error = new AError<>();
                    error.setErrorCode(UserError.USER_PERMISSION_NOT_FOUND);
                    return RevokePermissionForUserResult.builder()
                                    .error(error)
                                    .build();
                }
            }
            else
            {
                AError error = new AError<>();
                error.setErrorCode(UserError.USER_PERMISSION_NOT_FOUND);
                return RevokePermissionForUserResult.builder()
                                .error(error)
                                .build();
            }
        }
        else
        {
            AError error = new AError<>();
            error.setErrorCode(UserError.USER_NOT_FOUND);
            return RevokePermissionForUserResult.builder()
                            .error(error)
                            .build();
        }
    }


    @Transactional
    public UserModel save(UserModel user)
    {
        return dao.save(user);
    }


    public Optional<UserModel> getByID(UUID userID)
    {
        return dao.findById(userID);
    }


    public void delete(UUID userID)
    {
        dao.deleteById(userID);
    }


    public void deleteAll()
    {
        dao.deleteAll();
    }
}
