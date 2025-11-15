package com.dimi.project.project.user_group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dimi.core.data.DuplicateRecordException;
import com.dimi.project.TestBase;
import com.dimi.project.model.user_group.UserInUserGroupModel;
import com.dimi.project.model.user_group.UsersInUserGroupsDAO;
import com.dimi.project.user_group.AddUserToUserGroupResult;
import com.dimi.project.user_group.CreateUserGroupResult;
import com.dimi.project.user_group.UserGroupService;
import com.dimi.project.user_group.request.AddUserToUserGroupRequest;
import com.dimi.project.user_group.request.CreateUserGroupRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UserGroupServiceTest extends TestBase
{
    @Autowired UsersInUserGroupsDAO usersInUserGroupsDAO;
    @Autowired UserGroupService userGroupService;


    @BeforeEach
    void setup()
    {
        userGroupService.deleteAll();
        usersInUserGroupsDAO.deleteAll();
    }


    @Test
    void createGroup()
    {
        CreateUserGroupResult userGroup = userGroupService.createGroup(CreateUserGroupRequest.builder()
                        .name("group1")
                        .description("description1")
                        .build());
        assertThat(userGroup.getUserGroup()).isNotNull();
        assertThat(userGroup.getUserGroup().getName()).isEqualTo("group1");
        assertThat(userGroup.getUserGroup().getDescription()).isEqualTo("description1");
    }


    @Test
    void addUserToUserGroup()
    {
        CreateUserGroupResult userGroup = userGroupService.createGroup(CreateUserGroupRequest.builder()
                        .name("group1")
                        .description("description1")
                        .build());
        UUID userID = UUID.randomUUID();
        AddUserToUserGroupResult assignment1 = userGroupService.addUserToUserGroup(userGroup.getUserGroup().getId(), AddUserToUserGroupRequest.builder()
                        .userID(userID)
                        .build());
        Optional<UserInUserGroupModel> result1 = usersInUserGroupsDAO.findByUserGroupAndUserID(userGroup.getUserGroup(), userID);
        assertThat(result1.get().getUserGroup().getName()).isEqualTo(userGroup.getUserGroup().getName());
        assertThatThrownBy(() -> userGroupService.addUserToUserGroup(userGroup.getUserGroup().getId(), AddUserToUserGroupRequest.builder()
                        .userID(userID)
                        .build())).isInstanceOf(DuplicateRecordException.class);
    }


    @Test
    void removeUserFromUserGroup()
    {
        CreateUserGroupResult userGroup = userGroupService.createGroup(CreateUserGroupRequest.builder()
                        .name("group1")
                        .description("description1")
                        .build());
        UUID userID = UUID.randomUUID();
        AddUserToUserGroupResult assignment1 = userGroupService.addUserToUserGroup(userGroup.getUserGroup().getId(), AddUserToUserGroupRequest.builder()
                        .userID(userID)
                        .build());
        Optional<UserInUserGroupModel> result1 = usersInUserGroupsDAO.findByUserGroupAndUserID(userGroup.getUserGroup(), userID);
        assertThat(result1.get().getUserGroup().getName()).isEqualTo(userGroup.getUserGroup().getName());
        userGroupService.removeUserFromUserGroup(userGroup.getUserGroup().getId(), userID);
        Optional<UserInUserGroupModel> result2 = userGroupService.getUserInUserGroupByUserGroupIDAndUserID(userGroup.getUserGroup(), userID);
        assertThat(result2.isEmpty()).isTrue();
    }


    @Test
    void getAllUserGroupsUserBelongsTo()
    {
        CreateUserGroupResult userGroup1 = userGroupService.createGroup(CreateUserGroupRequest.builder()
                        .name("group1")
                        .description("description1")
                        .build());
        CreateUserGroupResult userGroup2 = userGroupService.createGroup(CreateUserGroupRequest.builder()
                        .name("group2")
                        .description("description2")
                        .build());
        UUID userID = UUID.randomUUID();
        AddUserToUserGroupResult assignment1 = userGroupService.addUserToUserGroup(userGroup1.getUserGroup().getId(), AddUserToUserGroupRequest.builder()
                        .userID(userID)
                        .build());
        AddUserToUserGroupResult assignment2 = userGroupService.addUserToUserGroup(userGroup2.getUserGroup().getId(), AddUserToUserGroupRequest.builder()
                        .userID(userID)
                        .build());
        List<UserInUserGroupModel> result1 = userGroupService.getAllUserGroupsUserBelongsTo(userID);
        assertThat(result1.size()).isEqualTo(2);
    }
}
