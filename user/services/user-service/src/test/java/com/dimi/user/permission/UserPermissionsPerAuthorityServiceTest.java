package com.dimi.user.permission;

import static org.assertj.core.api.Assertions.assertThat;

import com.dimi.user.TestBase;
import com.dimi.user.api.authority.CreateUserAuthorityAPI.NewUserAuthorityRequest;
import com.dimi.user.api.permission.CreateUserPermissionAPI.NewUserPermissionRequest;
import com.dimi.user.api.permission.CreateUserPermissionForAuthorityAPI.NewUserPermissionForAuthorityRequest;
import com.dimi.user.authority.CreateAuthorityResult;
import com.dimi.user.authority.UserAuthority;
import com.dimi.user.authority.UserAuthorityService;
import com.dimi.user.model.user.permission.UserPermissionsPerAuthorityModel;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UserPermissionsPerAuthorityServiceTest extends TestBase
{
    @Autowired UserAuthorityService userAuthorityService;
    @Autowired UserPermissionService userPermissionService;
    @Autowired UserPermissionsPerAuthorityService userPermissionsPerAuthorityService;


    @BeforeEach
    void setup()
    {
        userAuthorityService.deleteAll();
        userPermissionService.deleteAll();
        userPermissionsPerAuthorityService.deleteAll();
    }


    @Test
    void createPermissionForAuthority()
    {
        CreateAuthorityResult authority = userAuthorityService.createAuthority(NewUserAuthorityRequest.builder()
                        .authority(UserAuthority.USER.name())
                        .build());
        CreatePermissionResult permission = userPermissionService.createPermission(NewUserPermissionRequest.builder()
                        .name("perm1")
                        .description("descr1")
                        .build());
        CreatePermissionForAuthorityResult permissionForAuthority = userPermissionsPerAuthorityService.createPermissionForAuthority(NewUserPermissionForAuthorityRequest.builder()
                        .permissionID(permission.getPermission().getId())
                        .authorityID(authority.getAuthority().getId())
                        .build());
        assertThat(permissionForAuthority).isNotNull();
        assertThat(permissionForAuthority.getPermission().getAuthority().getAuthority()).isEqualTo(UserAuthority.USER.name());
        assertThat(permissionForAuthority.getPermission().getPermission().getName()).isEqualTo("perm1");
        assertThat(permissionForAuthority.getPermission().getCreatedAt().toString()).isNotBlank();
        assertThat(permissionForAuthority.getPermission().getUpdatedAt().toString()).isNotBlank();
    }


    @Test
    void getByPermissionIDAndAuthorityID()
    {
        CreateAuthorityResult authority = userAuthorityService.createAuthority(NewUserAuthorityRequest.builder()
                        .authority(UserAuthority.USER.name())
                        .build());
        CreatePermissionResult permission = userPermissionService.createPermission(NewUserPermissionRequest.builder()
                        .name("perm1")
                        .description("descr1")
                        .build());
        CreatePermissionForAuthorityResult permissionForAuthority = userPermissionsPerAuthorityService.createPermissionForAuthority(NewUserPermissionForAuthorityRequest.builder()
                        .permissionID(permission.getPermission().getId())
                        .authorityID(authority.getAuthority().getId())
                        .build());
        Optional<UserPermissionsPerAuthorityModel> wrap = userPermissionsPerAuthorityService.getByPermissionIDAndAuthorityID(permission.getPermission().getId(), authority.getAuthority().getId());
        assertThat(wrap.isPresent()).isTrue();
        UserPermissionsPerAuthorityModel result = wrap.get();
        assertThat(result.getAuthority().getAuthority()).isEqualTo(UserAuthority.USER.name());
        assertThat(result.getPermission().getName()).isEqualTo("perm1");
    }
}
