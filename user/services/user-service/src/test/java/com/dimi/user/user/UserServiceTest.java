package com.dimi.user.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.dimi.user.TestBase;
import com.dimi.user.authority.CreateAuthorityResult;
import com.dimi.user.authority.UserAuthority;
import com.dimi.user.authority.UserAuthorityService;
import com.dimi.user.authority.request.CreateUserAuthorityRequest;
import com.dimi.user.permission.CreatePermissionForAuthorityResult;
import com.dimi.user.permission.CreatePermissionResult;
import com.dimi.user.permission.UserPermissionService;
import com.dimi.user.permission.UserPermissionsPerAuthorityService;
import com.dimi.user.permission.request.CreateUserPermissionForAuthorityRequest;
import com.dimi.user.permission.request.CreateUserPermissionRequest;
import com.dimi.user.user.request.CreateUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest extends TestBase
{
    @Autowired UserService userService;
    @Autowired UserAuthorityService userAuthorityService;
    @Autowired UserPermissionService userPermissionService;
    @Autowired UserPermissionsPerAuthorityService userPermissionsPerAuthorityService;


    @BeforeEach
    void setup()
    {
        userService.deleteAll();
        userAuthorityService.deleteAll();
        userPermissionService.deleteAll();
        userPermissionsPerAuthorityService.deleteAll();
    }


    @Test
    void grantPermission()
    {
        // given
        CreateAuthorityResult authority = userAuthorityService.createAuthority(CreateUserAuthorityRequest.builder()
                        .authority(UserAuthority.USER.name())
                        .build());
        CreatePermissionResult permission = userPermissionService.createPermission(CreateUserPermissionRequest.builder()
                        .name("perm1")
                        .description("descr1")
                        .build());
        CreatePermissionForAuthorityResult permissionForAuthority = userPermissionsPerAuthorityService.createPermissionForAuthority(CreateUserPermissionForAuthorityRequest.builder()
                        .permissionID(permission.getPermission().getId())
                        .authorityID(authority.getAuthority().getId())
                        .build());
        CreateUserResult user = userService.createUser(CreateUserRequest.builder()
                        .username("me@me.com")
                        .build());
        // when
        GrantPermissionToUserResult result = userPermissionService.grantPermission(user.getUser().getId(), permissionForAuthority.getPermission().getId());
        // then
        assertThat(result.getUser()).isNotNull();
    }
}
